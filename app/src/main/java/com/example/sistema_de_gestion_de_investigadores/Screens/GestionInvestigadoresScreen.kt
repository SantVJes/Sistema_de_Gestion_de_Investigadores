@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens


import android.app.DatePickerDialog
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ArticuloInvestigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.InvestigadorLineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoInvestigador
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.AreaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.InvestigadorLineaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.LineaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectoInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectosViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun Investigadores_Screen(navController: NavController, appContainer: App_Container) {
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
                            text = " Gestión de Investigadores ",
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
                        x = (-14).dp,
                        y = (14).dp
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

            Body_investigadores(navController, appContainer)
        }
    }

}

@Composable
fun Body_investigadores(navController: NavController, appContainer: App_Container) {
    val investigadoresViewModel =
        investigadorViewModel(appContainer.provideInvestigadorRepository())

    // Recolectamos el Flow en un State observable
    val allInvestigadores by investigadoresViewModel.investigadores.collectAsState(emptyList())

    // Estado para controlar la edición de un investigador en base de datos
    var investigadorEditado by remember { mutableStateOf<Investigador?>(null) }
    var investigadorE by remember { mutableStateOf(false) }
    val areaTrabajoViewModel = AreaTrabajoViewModel(appContainer.provideAreaTrabajoRepository())
    val allAreasTrabajo by areaTrabajoViewModel.getAllAreasTrabajo().collectAsState(emptyList())
    val proyectoViewModel = ProyectosViewModel(appContainer.provideProyectoRepository())
    val articulosViewModel = ArticuloViewModel(appContainer.provideArticuloRepository())
    val tablaintermediade_ArticuloInvestigador =
        ArticuloInvestigadorViewModel(appContainer.provideArticuloInvestigadorRepository())
    val tablaintermediade_ProyectoInvestigador =
        ProyectoInvestigadorViewModel(appContainer.provideProyectoInvestigadorRepository())
    val lineaTrabajoViewModel = LineaTrabajoViewModel(appContainer.provideLineaTrabajoRepository())
    val tablaintermediade_InvestigadorLineaTrabajo =
        InvestigadorLineaTrabajoViewModel(appContainer.provideInvestigadorLineaTrabajoRepository())
    val alllinia_trabajo by lineaTrabajoViewModel.getAllLineasTrabajo().collectAsState(emptyList())
    val allproyecto by proyectoViewModel.proyectos.collectAsState(emptyList())
    val allarticulos by articulosViewModel.getAllArticulos().collectAsState(emptyList())

    // Variables para el formulario de agregar/editar investigador
    var nombre by remember { mutableStateOf("") }
    var id_unico by remember { mutableStateOf("0") }
    var especialidad by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var areaId by remember { mutableStateOf("") }
    var nivelSNII by remember { mutableStateOf<String?>(null) }
    var fechaSNII by remember { mutableStateOf("") }
    var mostrarFormulario by remember { mutableStateOf(false) }
    var expanded1 by remember { mutableStateOf(false) }
    var areaSeleccionadaNombre by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val opcionesSNII = listOf("No tiene", "Capacitacion", "Nivel I", "Nivel II", "Nivel III")
    var expanded2 by remember { mutableStateOf(false) }
    val lineasSeleccionadas = remember { mutableStateListOf<String>() }
    val linias_id = remember { mutableStateListOf<String>() }
    val linias_id_eliminar = remember { mutableStateListOf<String>() }
    var expanded3 by remember { mutableStateOf(false) }
    val proyectoSeleccionadas = remember { mutableStateListOf<String>() }
    val proyecto_id = remember { mutableStateListOf<String>() }
    var expand4 by remember { mutableStateOf(false) }
    val articulosSeleccionados = remember { mutableStateListOf<String>() }
    val articulos_id = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val ids_principal_arct = remember { mutableStateListOf<String>() }
    val ids_principal_proyext = remember { mutableStateListOf<String>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (mostrarFormulario) {


                // Formulario para agregar o editar investigador

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState()),

                    ) {

                    if (investigadorE == false) {
                        OutlinedTextField(
                            value = id_unico,
                            onValueChange = { id_unico = it },
                            label = { Text("Ingrese el id_unico del nuevo investigador") },
                            modifier = Modifier.fillMaxWidth()
                        )


                        val idInt = id_unico.toIntOrNull()
                        if (idInt != null && allInvestigadores.any { it.id == idInt }) {
                            id_unico = ""
                            Toast.makeText(context, "ID ya está asignado", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del Investigador") },
                        modifier = Modifier.fillMaxWidth()
                    )


                    OutlinedTextField(
                        value = especialidad,
                        onValueChange = { especialidad = it },
                        label = { Text("Especialidad") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = carrera,
                        onValueChange = { carrera = it },
                        label = { Text("Carrera") },
                        modifier = Modifier.fillMaxWidth()
                    )




                    ExposedDropdownMenuBox(
                        expanded = expanded1,
                        onExpandedChange = { expanded1 = !expanded1 } // alterna al hacer clic
                    ) {
                        OutlinedTextField(
                            value = areaSeleccionadaNombre ?: "Selecciona un área",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Área de trabajo") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1)
                            },
                            modifier = Modifier
                                .menuAnchor() // importante para alinear bien el menú
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded1,
                            onDismissRequest = { expanded1 = false }
                        ) {
                            allAreasTrabajo.forEach { area ->
                                DropdownMenuItem(
                                    text = { Text(area.nombre) },
                                    onClick = {
                                        areaId = area.id.toString()
                                        areaSeleccionadaNombre = area.nombre
                                        expanded1 = false
                                    }
                                )
                            }
                        }
                    }


                    // Selector para Nivel SNII


                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = nivelSNII ?: "No tiene",
                            onValueChange = {},
                            label = { Text("Nivel SNII") },
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
                            opcionesSNII.forEach { opcion ->
                                DropdownMenuItem(
                                    onClick = {
                                        nivelSNII = if (opcion == "No tiene") null else opcion
                                        expanded = false
                                    },
                                    text = { Text(opcion) }
                                )
                            }
                        }
                    }

                    if (nivelSNII != null && nivelSNII != "Capacitacion") {

                        // Mostrar el DatePickerDialog al hacer clic
                        val datePickerDialog = remember {
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    fechaSNII = dateFormatter.format(calendar.time)
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
                                    text = "Fecha SNII",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (fechaSNII.isNotEmpty()) fechaSNII else "Selecciona una fecha",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (fechaSNII.isNotEmpty()) Color.Black else Color.LightGray
                                )
                            }
                        }

                    }





                    ExposedDropdownMenuBox(
                        expanded = expanded2,
                        onExpandedChange = { expanded2 = !expanded2 }
                    ) {
                        OutlinedTextField(
                            value = if (lineasSeleccionadas.isNotEmpty())
                                lineasSeleccionadas.joinToString(", ")
                            else
                                "Selecciona una o más líneas de trabajo",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Líneas de trabajo") },
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
                            alllinia_trabajo.forEach { linia ->
                                // ¿Está seleccionada?
                                val isSelected = lineasSeleccionadas.contains(linia.nombre)
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = isSelected,
                                                onCheckedChange = null // no se usa aquí, solo indica estado
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = linia.nombre)
                                        }
                                    },
                                    onClick = {
                                        if (isSelected) {
                                            lineasSeleccionadas.remove(linia.nombre)
                                            linias_id.remove(linia.id.toString())
                                        } else {
                                            lineasSeleccionadas.add(linia.nombre)
                                            linias_id.add(linia.id.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }




                    ExposedDropdownMenuBox(
                        expanded = expanded3,
                        onExpandedChange = { expanded3 = !expanded3 }
                    ) {
                        OutlinedTextField(
                            value = if (proyectoSeleccionadas.isNotEmpty())
                                proyectoSeleccionadas.joinToString(", ")
                            else
                                "Selecciona una o más Proyectos de investigacion o Ninguno",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Proyectos de investigacion") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded3)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded3,
                            onDismissRequest = { expanded3 = false }
                        ) {
                            allproyecto.forEach { proyect ->
                                // ¿Está seleccionada?
                                val isSelected = proyectoSeleccionadas.contains(proyect.nombre)
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = isSelected,
                                                onCheckedChange = null // no se usa aquí, solo indica estado
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = proyect.nombre)
                                        }
                                    },
                                    onClick = {
                                        if (isSelected) {
                                            proyectoSeleccionadas.remove(proyect.nombre)
                                            proyecto_id.remove(proyect.id.toString())
                                        } else {
                                            proyectoSeleccionadas.add(proyect.nombre)
                                            proyecto_id.add(proyect.id.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }

                    //articulos


                    ExposedDropdownMenuBox(
                        expanded = expand4,
                        onExpandedChange = { expand4 = !expand4 }
                    ) {
                        OutlinedTextField(
                            value = if (articulosSeleccionados.isNotEmpty())
                                articulosSeleccionados.joinToString(", ")
                            else
                                "Selecciona una o más Artculos de investigacion o Ninguno",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Articulos de investigacion") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand4)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expand4,
                            onDismissRequest = { expand4 = false }
                        ) {
                            allarticulos.forEach { artc ->
                                // ¿Está seleccionada?
                                val isSelected = articulosSeleccionados.contains(artc.nombre)
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = isSelected,
                                                onCheckedChange = null // no se usa aquí, solo indica estado
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = artc.nombre)
                                        }
                                    },
                                    onClick = {
                                        if (isSelected) {
                                            articulosSeleccionados.remove(artc.nombre)
                                            articulos_id.remove(artc.id.toString())
                                        } else {
                                            articulosSeleccionados.add(artc.nombre)
                                            articulos_id.add(artc.id.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }


                    val relacioneslinia_editor =
                        tablaintermediade_InvestigadorLineaTrabajo.getLineasPorInvestigador(
                            investigadorEditado!!.id
                        ).collectAsState(emptyList())
                    val relacionesProyecto_editor =
                        tablaintermediade_ProyectoInvestigador.getProyectoPorInvestigador(
                            investigadorEditado!!.id
                        ).collectAsState(emptyList())
                    val relacionesArticulo_editor =
                        tablaintermediade_ArticuloInvestigador.getArticuloPorInvestigador(
                            investigadorEditado!!.id
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
                                if (investigadorE == false) {
                                    // Si estamos añadiendo un investigador nuevo
                                    val nuevoInvestigador =
                                        Investigador( // O cualquier valor que se maneje para el nuevo investigador
                                            id = id_unico.toInt(),
                                            nombre = nombre,
                                            especialidad = especialidad,
                                            carrera = carrera,
                                            areaId = areaId.toInt(),
                                            nivelSNII = nivelSNII,
                                            fechaSNII = fechaSNII
                                        )
                                    investigadoresViewModel.incertInvestigador(nuevoInvestigador)






                                    for (i in linias_id) {
                                        val nuevarealcion = InvestigadorLineaTrabajo(
                                            investigadorId = nuevoInvestigador.id,
                                            lineaTrabajoId = i.toInt()
                                        )
                                        tablaintermediade_InvestigadorLineaTrabajo.incertRelacion(
                                            nuevarealcion
                                        )
                                    }

                                    for (i in articulos_id) {
                                        val nuevarealcion = ArticuloInvestigador(
                                            articuloId = i.toInt(),
                                            investigadorId = nuevoInvestigador.id,
                                            esPrincipal = false
                                        )
                                        tablaintermediade_ArticuloInvestigador.incertRelacion(
                                            nuevarealcion
                                        )
                                    }

                                    for (i in proyecto_id) {
                                        val nuevarealcion = ProyectoInvestigador(
                                            proyectoId = i.toInt(),
                                            investigadorId = nuevoInvestigador.id,
                                            esPrincipal = false
                                        )
                                        tablaintermediade_ProyectoInvestigador.incertRelacion(
                                            nuevarealcion
                                        )
                                    }


                                } else {
                                    // Si estamos editando un investigador existente
                                    val investigadorEditado = investigadorEditado!!.copy(
                                        id = investigadorEditado!!.id,
                                        nombre = nombre,
                                        especialidad = especialidad,
                                        carrera = carrera,
                                        areaId = areaId.toInt(),
                                        nivelSNII = nivelSNII,
                                        fechaSNII = fechaSNII
                                    )
                                    investigadoresViewModel.updateInvestigador(investigadorEditado)
                                    //eliminar las anteriores relaciones de la tabla intermedia


                                    for (i in relacioneslinia_editor.value) {
                                        tablaintermediade_InvestigadorLineaTrabajo.deleteRelacion(i)
                                    }

                                    for (i in relacionesProyecto_editor.value) {
                                        i?.let { relacion ->
                                            if (relacion.esPrincipal != true) {  // Si no es principal, lo eliminamos
                                                tablaintermediade_ProyectoInvestigador.deleteRelacion(
                                                    relacion
                                                )
                                            } else {
                                                ids_principal_proyext.add(relacion.proyectoId.toString())
                                            }
                                        }

                                    }
                                    for (i in relacionesArticulo_editor.value) {
                                        i?.let { relacion ->
                                            if (relacion.esPrincipal != true) {  // Si no es principal, lo eliminamos
                                                tablaintermediade_ArticuloInvestigador.deleteRelacion(
                                                    relacion
                                                )
                                            } else {
                                                ids_principal_arct.add(relacion.articuloId.toString())
                                            }
                                        }
                                    }



                                    for (i in linias_id) {
                                        val nuevarealcion = InvestigadorLineaTrabajo(
                                            investigadorId = investigadorEditado.id,
                                            lineaTrabajoId = i.toInt()
                                        )
                                        tablaintermediade_InvestigadorLineaTrabajo.incertRelacion(
                                            nuevarealcion
                                        )
                                    }
                                    for (i in articulos_id) {
                                        if (!ids_principal_arct.contains(i)) {
                                            val nuevarealcion = ArticuloInvestigador(
                                                articuloId = i.toInt(),
                                                investigadorId = investigadorEditado.id,
                                                esPrincipal = false
                                            )
                                            tablaintermediade_ArticuloInvestigador.incertRelacion(
                                                nuevarealcion
                                            )
                                        }
                                    }
                                    for (i in proyecto_id) {
                                        if (!ids_principal_proyext.contains(i)) {
                                            val nuevarealcion = ProyectoInvestigador(
                                                proyectoId = i.toInt(),
                                                investigadorId = investigadorEditado.id,
                                                esPrincipal = false
                                            )
                                            tablaintermediade_ProyectoInvestigador.incertRelacion(
                                                nuevarealcion
                                            )
                                        }
                                    }


                                }
                                // Limpiar el formulario
                                nombre = ""
                                especialidad = ""
                                carrera = ""
                                areaId = ""
                                nivelSNII = null
                                fechaSNII = ""
                                mostrarFormulario = false
                                investigadorEditado = null

                                lineasSeleccionadas.clear()
                                linias_id.clear()
                                linias_id_eliminar.clear()
                                ids_principal_arct.clear()
                                ids_principal_proyext.clear()


                                proyectoSeleccionadas.clear()
                                proyecto_id.clear()

                                articulosSeleccionados.clear()
                                articulos_id.clear()
                                // Para que se cierre el teclado
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 30.dp)
                        ) {
                            Text(if (investigadorE == false) "Añadir Investigador" else "Editar Investigador")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            shape = RectangleShape,


                            onClick = {

                                mostrarFormulario = false
                                nombre = ""
                                especialidad = ""
                                carrera = ""
                                areaId = ""
                                nivelSNII = null
                                fechaSNII = ""
                                mostrarFormulario = false
                                investigadorEditado = null

                                lineasSeleccionadas.clear()
                                linias_id.clear()
                                linias_id_eliminar.clear()
                                ids_principal_arct.clear()
                                ids_principal_proyext.clear()


                                proyectoSeleccionadas.clear()
                                proyecto_id.clear()

                                articulosSeleccionados.clear()
                                articulos_id.clear()

                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Buscar Investigador")
                        }

                    }
                }


            }

            Spacer(modifier = Modifier.height(20.dp))

            // Usamos la lista recolectada por collectAsState


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(allInvestigadores) { investigador ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        val articulos by tablaintermediade_ArticuloInvestigador.getArticuloPorInvestigador(
                            investigador.id
                        ).collectAsState(emptyList())

                        val nombresArticulos = articulos.mapNotNull { articulo ->
                            val nombreState =
                                articulosViewModel.getArticuloById(articulo!!.articuloId.toInt())
                                    .collectAsState(initial = null)
                            val principalin = nombreState.value?.nombre
                            principalin?.let {
                                if (articulo.esPrincipal)
                                    "$it es Principal    "
                                else
                                    it
                            }


                        }

                        val area by areaTrabajoViewModel.getAreaTrabajoById(investigador.areaId)
                            .collectAsState(null)

                        val lineas by tablaintermediade_InvestigadorLineaTrabajo.getLineasPorInvestigador(
                            investigador.id
                        ).collectAsState(emptyList())

                        val nombresLineas = lineas.map { linea ->
                            lineaTrabajoViewModel.getLineaTrabajoById(linea.lineaTrabajoId)
                                .collectAsState(null).value?.nombre ?: ""


                        }

                        val proyectos by tablaintermediade_ProyectoInvestigador.getProyectoPorInvestigador(
                            investigador.id
                        ).collectAsState(emptyList())

                        val nombresCompletos = proyectos.mapNotNull { proyecto ->

                            val nombreState =
                                proyectoViewModel.getProyectoById(proyecto!!.proyectoId.toInt())
                                    .collectAsState(initial = null)

                            val nombre = nombreState.value?.nombre
                            nombre?.let {
                                if (proyecto.esPrincipal)
                                    "$it es Principal    "
                                else
                                    it
                            }

                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = investigador.nombre,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1976D2)
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Especialidad: ${investigador.especialidad}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "Carrera: ${investigador.carrera}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            if (investigador.nivelSNII != null) {
                                Text(
                                    "Nivel SNII: ${investigador.nivelSNII}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "Fecha SNII: ${investigador.fechaSNII}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            } else {
                                Text(
                                    "Nivel SNII: No Tiene",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Text(
                                "Área: ${area?.nombre}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            // Mini tarjeta para Líneas de Trabajo
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6E5)),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Líneas de Trabajo",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = nombresLineas.joinToString(
                                            ", ",
                                            prefix = "(",
                                            postfix = ")"
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF388E3C)
                                    )
                                }
                            }

                            // Mini tarjeta para Proyectos
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
                                        text = "Proyectos",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = nombresCompletos.joinToString(", "),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF880606)
                                    )
                                }
                            }

                            // Mini tarjeta para Artículos
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Artículos",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = nombresArticulos.joinToString(", "),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF1976D2)
                                    )
                                }
                            }

                            // Botones de eliminar y editar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        investigadoresViewModel.deleteInvestigador(investigador)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar Investigador",
                                        tint = Color.Red
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                IconButton(
                                    onClick = {
                                        mostrarFormulario = true
                                        // Cargar los datos del investigador
                                        // Cargar los datos del investigador en el formulario
                                        investigadorEditado = investigador
                                        investigadorE = true
                                        nombre = investigador.nombre
                                        especialidad = investigador.especialidad
                                        carrera = investigador.carrera
                                        areaId = investigador.areaId.toString()
                                        nivelSNII = investigador.nivelSNII
                                        fechaSNII = investigador.fechaSNII ?: ""


                                        val linia_edit = lineas

                                        for (linea in linia_edit) {
                                            linias_id.add(linea.lineaTrabajoId.toString())
                                            linias_id_eliminar.add(linea.lineaTrabajoId.toString())
                                        }

                                        for (linia in nombresLineas) {
                                            lineasSeleccionadas.add(linia)

                                        }

                                        for (proyecto in nombresCompletos) {
                                            val limpio = proyecto.replace(" es Principal    ", "")
                                            proyectoSeleccionadas.add(limpio)
                                        }
                                        for (proyecto in proyectos) {

                                            proyecto_id.add(proyecto!!.proyectoId.toString())
                                        }

                                        for (artc in nombresArticulos) {
                                            val limpio = artc.replace(" es Principal    ", "")
                                            articulosSeleccionados.add(limpio)
                                        }
                                        for (artc in articulos) {
                                            articulos_id.add(artc!!.articuloId.toString())
                                        }


                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Editar Investigador",
                                        tint = Color.Blue
                                    )
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
                mostrarFormulario = true

                nombre = ""
                especialidad = ""
                carrera = ""
                areaId = ""
                nivelSNII = null
                fechaSNII = ""
                investigadorE = false

                investigadorEditado = Investigador(
                    nombre = "",
                    especialidad = "",
                    carrera = "",
                    areaId = 0,
                    nivelSNII = null,
                    fechaSNII = ""
                )


                lineasSeleccionadas.clear()
                linias_id.clear()
                ids_principal_arct.clear()
                ids_principal_proyext.clear()


                proyectoSeleccionadas.clear()
                proyecto_id.clear()

                articulosSeleccionados.clear()
                articulos_id.clear()


            },
            containerColor = Color(0xFF00BCD4),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(1.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir Investigador")
        }

    }

}


@Preview(showBackground = true)
@Composable
fun Greeting_investigadores() {
    Investigadores_Screen(
        NavController(LocalContext.current),
        App_Container(LocalContext.current)
    )
}



