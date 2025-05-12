@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Articulo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ArticuloInvestigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoHerramienta
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoInvestigador
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun Articulos_Screen(navController: NavController, appContainer: App_Container) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF00BCD4),
                    titleContentColor = Color.White,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = " Articulos de Investigación ",
                            style = TextStyle(
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF00BCD4),
                contentColor = Color.Black
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Creado Por Jesus Santiago Velasco ",
                )
            }
        },
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate(Directorio.HomeScreen)

                },
                modifier = Modifier
                    .padding(0.dp)
                    .offset(
                        x = (-13).dp,
                        y = (13).dp
                    )// Espaciado desde los bordes, puedes ajustarlo


            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "retron"
                )
            }

        },
        floatingActionButtonPosition = FabPosition.Start,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Body_Articulo(navController, appContainer)
        }
    }
}


@Composable
fun Body_Articulo(navController: NavController, appContainer: App_Container) {

    //Estados para controlar la base de datos
    val tablaintermediade_ArticuloInvestigador =
        ArticuloInvestigadorViewModel(appContainer.provideArticuloInvestigadorRepository())
    val articulosViewModel = ArticuloViewModel(appContainer.provideArticuloRepository())
    val allArticulos by articulosViewModel.getAllArticulos().collectAsState(emptyList())
    val investigadoresViewModel =
        investigadorViewModel(appContainer.provideInvestigadorRepository())


    // Recolectamos el Flow en un State observable
    val allInvestigadores by investigadoresViewModel.investigadores.collectAsState(emptyList())
    var investigador_principal by remember { mutableStateOf<String?>(null) }
    var investigadoresDisponibles = mutableListOf<Investigador>()
    var art by remember { mutableStateOf<Articulo?>(null) }

    //Variables a utilizar
    var ver_form by remember { mutableStateOf(false) }
    var id_unico by remember { mutableStateOf("") }
    var id_unicoEd by remember { mutableStateOf("") }
    var id_principal by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var revista by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") } // Usar String para facilitar la entrada
    var fechaPublicacion by remember { mutableStateOf("") }
    var doi by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var edit by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var investigador_proyecto = remember { mutableStateListOf<String>() }
    var investigador_id = remember { mutableStateListOf<String>() }

    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier.padding(16.dp)) {

            if (ver_form) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    if (edit == false) {
                        OutlinedTextField(
                            value = id_unico,
                            onValueChange = { id_unico = it },
                            label = { Text("ID") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre de la Línea de Trabajo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = revista,
                        onValueChange = { revista = it },
                        label = { Text("Revista") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = pais,
                        onValueChange = { pais = it },
                        label = { Text("País") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Mostrar el DatePickerDialog al hacer clic
                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                fechaPublicacion = dateFormatter.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() } // Toda el área es clickeable
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

                            .padding(8.dp) // Espaciado interno para parecerse a un TextField
                    ) {
                        Column {
                            Text(
                                text = "Fecha de Publicació",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (fechaPublicacion.isNotEmpty()) fechaPublicacion else "Selecciona una fecha de publicacion",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fechaPublicacion.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }

                    OutlinedTextField(
                        value = doi,
                        onValueChange = { doi = it },
                        label = { Text("DOI") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        label = { Text("URL") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = investigador_principal
                                ?: "selecione  el investigador principal ",
                            onValueChange = {},
                            label = { Text("Investigador Principal del articulo") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            allInvestigadores.forEach { opcion ->
                                DropdownMenuItem(
                                    onClick = {
                                        investigador_principal = opcion.nombre
                                        expanded = false
                                    },
                                    text = { Text(opcion.nombre) }
                                )
                            }
                        }
                    }


                    investigadoresDisponibles =
                        allInvestigadores.filter { it.nombre !in investigador_principal.toString() }
                            .toMutableList()

                    //añadir investigadores
                    ExposedDropdownMenuBox(
                        expanded = expanded2,
                        onExpandedChange = { expanded2 = !expanded2 }
                    ) {
                        OutlinedTextField(
                            value = if (investigador_proyecto.isNotEmpty())
                                investigador_proyecto.joinToString(", ")
                            else
                                "Seleccione a un investigador principal ",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Investigador principal") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded2,
                            onDismissRequest = { expanded2 = false }
                        ) {
                            investigadoresDisponibles.forEach { investigador ->
                                // ¿Está seleccionada?

                                val isSelected = investigador_proyecto.contains(investigador.nombre)
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = isSelected,
                                                onCheckedChange = null // no se usa aquí, solo indica estado
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = investigador.nombre)
                                        }
                                    },
                                    onClick = {
                                        if (isSelected) {
                                            investigador_proyecto.remove(investigador.nombre)
                                            investigador_id.remove(investigador.id.toString())
                                        } else {
                                            investigador_proyecto.add(investigador.nombre)
                                            investigador_id.add(investigador.id.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }
                    val tablaintermediade =
                        tablaintermediade_ArticuloInvestigador.getInvestigadoresPorArticulo(
                            id_unicoEd.toInt()
                        ).collectAsState(emptyList())

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),

                        ) {
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = {

                                if (edit == false) {

                                    val newarticulo = Articulo(
                                        id = id_unico.toInt(),
                                        nombre = nombre,
                                        revista = revista,
                                        pais = pais,
                                        anio = fechaPublicacion.split("/")[2].toInt(),
                                        fechaPublicacion = fechaPublicacion,
                                        doi = doi,
                                        url = url,
                                    )
                                    articulosViewModel.incertArticulo(newarticulo)
                                    val id_princ =
                                        allInvestigadores.find { it.nombre !in investigador_principal.toString() }

                                    val newarticuloinvestigador = ArticuloInvestigador(
                                        articuloId = id_unico.toInt(),
                                        investigadorId = id_princ!!.id,
                                        esPrincipal = true

                                    )
                                    tablaintermediade_ArticuloInvestigador.incertRelacion(
                                        newarticuloinvestigador
                                    )
                                    for (i in investigador_id) {
                                        val newarticuloinvestigador = ArticuloInvestigador(
                                            articuloId = id_unico.toInt(),
                                            investigadorId = i.toInt(),
                                            esPrincipal = false
                                        )
                                        tablaintermediade_ArticuloInvestigador.incertRelacion(
                                            newarticuloinvestigador
                                        )

                                    }
                                    nombre = ""
                                    revista = ""
                                    pais = ""
                                    anio = ""
                                    fechaPublicacion = ""
                                    doi = ""
                                    url = ""
                                    investigador_proyecto.clear()
                                    investigador_id.clear()
                                    investigador_principal = ""
                                    investigadoresDisponibles.clear()
                                    art = null
                                    id_unico = ""






                                    ver_form = false

                                } else {


                                    //Editar articulo
                                    art!!.nombre = nombre
                                    art!!.revista = revista
                                    art!!.pais = pais
                                    art!!.anio = fechaPublicacion.split("/")[2].toInt()

                                    art!!.fechaPublicacion = fechaPublicacion
                                    art!!.doi = doi
                                    art!!.url = url


                                    articulosViewModel.updateArticulo(art!!)


                                    for (i in tablaintermediade.value) {
                                        i?.let { relacion ->
                                            if (relacion.esPrincipal != true) {  // Si no es principal, lo eliminamos
                                                tablaintermediade_ArticuloInvestigador.deleteRelacion(
                                                    relacion
                                                )
                                            } else {
                                                id_principal = relacion.investigadorId.toString()
                                            }
                                        }
                                    }
                                    //incertar las nuevas relacion
                                    for (i in investigador_id) {

                                        if (i != id_principal) {
                                            val newarticuloinvestigador = ArticuloInvestigador(
                                                articuloId = id_unicoEd.toInt(),
                                                investigadorId = i.toInt(),
                                                esPrincipal = false
                                            )
                                            tablaintermediade_ArticuloInvestigador.incertRelacion(
                                                newarticuloinvestigador
                                            )
                                        }
                                    }
                                    nombre = ""
                                    revista = ""
                                    pais = ""
                                    anio = ""
                                    fechaPublicacion = ""
                                    doi = ""
                                    url = ""
                                    investigador_proyecto.clear()
                                    investigador_id.clear()
                                    investigador_principal = ""
                                    investigadoresDisponibles.clear()
                                    art = null
                                    id_unico = ""
                                    ver_form = false
                                }
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 25.dp)
                        ) {
                            Text(if (edit == false) "Añadir linea" else "Editar linea")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            shape = RectangleShape,
                            onClick = {
                                edit = false
                                nombre = ""
                                revista = ""
                                pais = ""
                                anio = ""
                                fechaPublicacion = ""
                                doi = ""
                                url = ""
                                id_unico = ""
                                id_unicoEd = ""
                                investigador_proyecto.clear()
                                investigador_id.clear()
                                investigador_principal = ""
                                investigadoresDisponibles.clear()
                                art = null

                                ver_form = false
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Salir form")
                        }
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            {
                items(allArticulos) { articulo ->
                    val inveart by tablaintermediade_ArticuloInvestigador.getInvestigadoresPorArticulo(
                        articulo.id
                    ).collectAsState(emptyList())

                    val nombresCompletos = inveart.mapNotNull { investigador ->

                        val nombreState =
                            investigadoresViewModel.getInvestigadorById(investigador.investigadorId)
                                .collectAsState(null)

                        val principalin = nombreState.value?.nombre
                        principalin?.let {
                            if (investigador.esPrincipal)
                                "$it es Principal    "
                            else
                                it
                        }
                    }
                    id_unicoEd = articulo.id.toString()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)) // Color más neutro y moderno
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = articulo.nombre,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A237E)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "📘 Revista: ${articulo.revista}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "🌎 País: ${articulo.pais}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "📅 Año: ${articulo.anio}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "🗓️ Fecha de Publicación: ${articulo.fechaPublicacion}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "🔗 DOI: ${articulo.doi}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "🌐 URL: ${articulo.url}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(
                                            0xFF1565C0
                                        )
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Mini tarjeta
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE6DEF3)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Investigadores Autores del Articulo",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = nombresCompletos.joinToString(", "),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF880606)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                // Botones de eliminar y editar
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp, end = 14.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            articulosViewModel.deleteArticulo(articulo)

                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar Articulo",
                                            tint = Color.Red
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(
                                        onClick = {
                                            edit = true
                                            id_unicoEd = articulo.id.toString()
                                            nombre = articulo.nombre
                                            revista = articulo.revista
                                            pais = articulo.pais
                                            anio = articulo.anio.toString()
                                            fechaPublicacion = articulo.fechaPublicacion
                                            doi = articulo.doi
                                            url = articulo.url


                                            art = articulo

                                            ver_form = true
                                            investigador_principal = nombresCompletos.find {
                                                it.contains("es Principal    ")
                                            } ?: ""

                                            for (i in nombresCompletos) {
                                                if (!i.contains("es Principal    ")) {
                                                    investigador_proyecto.add(i)
                                                }


                                            }
                                            for (i in inveart) {
                                                investigador_id.add(i!!.investigadorId.toString())

                                            }


                                        },
                                    ) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "Editar Articulo",
                                            tint = Color.Blue
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }
        // Botón para añadir un investigador
        FloatingActionButton(
            onClick = {
                ver_form = true
                nombre = ""
                id_unico = ""
                investigador_principal = ""
                investigador_proyecto.clear()
                investigador_id.clear()
                edit = false
            },
            containerColor = Color(0xFF00BCD4),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(1.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir Proyecto")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun Articulos_ScreenPreview() {

    Articulos_Screen(
        navController = NavController(LocalContext.current),
        appContainer = App_Container(LocalContext.current)
    )
}