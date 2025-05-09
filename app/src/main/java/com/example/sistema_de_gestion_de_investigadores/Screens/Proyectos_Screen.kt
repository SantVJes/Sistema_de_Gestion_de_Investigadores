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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.traceEventEnd
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
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.HerramientaViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectoHerramientaViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectoInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectosViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import kotlinx.coroutines.flow.mapNotNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun Proyecto_screen(navController: NavController, appContainer: App_Container) {

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
                            text = " Proyectos En Curso ",
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
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "retron")
            }

        },
        floatingActionButtonPosition = FabPosition.Start,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Body_proyecto(navController, appContainer)
        }
    }

}

@Composable
fun Body_proyecto(navController: NavController, appContainer: App_Container) {

    //Estados para controlar la base de datos
    val proyectoViewModel = ProyectosViewModel(appContainer.provideProyectoRepository())
    val tablaintermediade_ProyectoInvestigador =
        ProyectoInvestigadorViewModel(appContainer.provideProyectoInvestigadorRepository())
    val allproyecto by proyectoViewModel.getAllProyectos().collectAsState(emptyList())
    val investigadoresViewModel =
        investigadorViewModel(appContainer.provideInvestigadorRepository())
    val allInvestigadores by investigadoresViewModel.investigadores.collectAsState(emptyList())
    val herramientaViewModel = HerramientaViewModel(appContainer.provideHerramientaRepository())
    val allherramientas by herramientaViewModel.getAllHerramientas().collectAsState(emptyList())
    val tablaIntermedia_HerramientaProyecto = ProyectoHerramientaViewModel(appContainer.provideProyectoHerramientaRepository())


    //Variables a utilizar en el formulario y en la app en general
    var ver_form by remember { mutableStateOf(false) }
    var id_unico by remember { mutableStateOf("0") }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha_inicio by remember { mutableStateOf("") }
    var fecha_fin by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    var expanded1 by remember { mutableStateOf(false) }
    var herramientaUtilizada   = remember { mutableStateListOf<String>() }
    var herramienta_id = remember { mutableStateListOf<String>() }
    var proyecto_ by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var investigador_proyecto = remember { mutableStateListOf<String>() }
    var investigador_id = remember { mutableStateListOf<String>() }
    var investigador_principal by remember { mutableStateOf("") }
    var investigadoresDisponibles = mutableListOf<Investigador>()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (modifier = Modifier.padding(16.dp))
        {

            //Formulario para crear un nuevo proyecto

            if (ver_form) {
                Column(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .verticalScroll(rememberScrollState())
                ){

                    OutlinedTextField(
                        value = id_unico,
                        onValueChange = { id_unico = it },
                        label = { Text("Ingrese el Id unico del Proyecto") },
                        modifier = Modifier.fillMaxWidth()
                    )




                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Ingrese el Nombre del Proyecto") },
                        modifier = Modifier.fillMaxWidth()
                    )


                    // Mostrar el DatePickerDialog al hacer clic
                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                fecha_inicio = dateFormatter.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                    }
                    // Mostrar el DatePickerDialog al hacer clic
                    val datePickerDialog2 = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                fecha_fin = dateFormatter.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() } // Toda el área es clickeable
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

                            .padding(8.dp) // Espaciado interno para parecerse a un TextField
                    ) {
                        Column {
                            Text(
                                text = "Fecha de Inicio",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (fecha_inicio.isNotEmpty()) fecha_inicio else "Selecciona una fecha de Inicio",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fecha_inicio.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog2.show() } // Toda el área es clickeable
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

                            .padding(8.dp) // Espaciado interno para parecerse a un TextField
                    ) {
                        Column {
                            Text(
                                text = "Fecha de Finalizacion",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (fecha_fin.isNotEmpty()) fecha_fin else "Selecciona una fecha Finalizacion",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fecha_fin.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = investigador_principal ?: "No tiene",
                            onValueChange = {},
                            label = { Text("Investigador Principal") },
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
                                        expanded = false
                                    },
                                    text = { Text(opcion.nombre) }
                                )
                            }
                        }
                    }






                    ExposedDropdownMenuBox(
                        expanded = expanded1,
                        onExpandedChange = { expanded1 = !expanded1 }
                    ) {
                        OutlinedTextField(
                            value = if (herramientaUtilizada.isNotEmpty())
                                herramientaUtilizada.joinToString(", ")
                            else
                                "Selecciona una o más herramientas para el proyecto",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Herramientas ") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded1,
                            onDismissRequest = { expanded1 = false }
                        ) {
                            allherramientas.forEach { herramienta ->
                                // ¿Está seleccionada?
                                val isSelected = herramientaUtilizada.contains(herramienta.nombre)
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = isSelected,
                                                onCheckedChange = null // no se usa aquí, solo indica estado
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = herramienta.nombre)
                                        }
                                    },
                                    onClick = {
                                        if (isSelected) {
                                            herramientaUtilizada.remove(herramienta.nombre)
                                            herramienta_id.remove(herramienta.id.toString())
                                        } else {
                                            herramientaUtilizada.add(herramienta.nombre)
                                            herramienta_id.add(herramienta.id.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }

                     investigadoresDisponibles = allInvestigadores.filter { it.nombre !in investigador_principal }.toMutableList()

                    //añadir investigadores
                    ExposedDropdownMenuBox(
                        expanded = expanded2,
                        onExpandedChange = { expanded2 = !expanded2 }
                    ) {
                        OutlinedTextField(
                            value = if (investigador_proyecto.isNotEmpty())
                                investigador_proyecto.joinToString(", ")
                            else
                                "Seleccione a un investigador o mas ",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Investigadores del Proyecto") },
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

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),

                        ) {
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = {
                                ver_form = false

                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 25.dp)
                        ) {
                            Text(if (proyecto_ == false) "Añadir Investigador" else "Editar Investigador")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            shape = RectangleShape,

                            onClick = {
                                ver_form = false
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Salir form")
                        }
                    }








                }

            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp))
            {

              items(allproyecto){ proyecto ->



                  Card (
                      modifier = Modifier
                          .fillMaxWidth(),
                      shape = RoundedCornerShape(16.dp),
                      colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                      elevation = CardDefaults.cardElevation(6.dp)
                  ){
                      Column(modifier = Modifier.padding(16.dp)) {
                          Text(
                              text = proyecto.nombre,
                              fontSize = 22.sp,
                              fontWeight = FontWeight.Bold,
                              color = Color(0xFF1976D2)
                          )
                          Spacer(modifier = Modifier.height(4.dp))

                          Text("Fecha de Inicio: ${proyecto.fechaInicio}", fontSize = 15.sp , fontWeight = FontWeight.SemiBold )
                          Spacer(modifier = Modifier.height(2.dp))
                          Text("Fecha de Fin: ${proyecto.fechaFin}", fontSize = 15.sp , fontWeight = FontWeight.SemiBold )

                      }


                      val herramientProyec by tablaIntermedia_HerramientaProyecto.getHerramientasPorProyecto(proyecto.id).collectAsState(emptyList())

                      val nombreherram = herramientProyec.mapNotNull { herramientaProyecto ->
                          val herramientaState = herramientaViewModel.getHerramientaById(herramientaProyecto.herramientaId).collectAsState(null)
                          herramientaState.value?.let { "${it.nombre} (${it.tipo})" }
                      }




                      // Mini tarjeta para las herramientas
                      Card(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(top = 8.dp),
                          shape = RoundedCornerShape(12.dp),
                          colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD)),
                          elevation = CardDefaults.cardElevation(4.dp)
                      ) {
                          Column(modifier = Modifier.padding(12.dp)) {
                              Text(
                                  text = "Herramientas Siendo utilizadas en El Proyecto",
                                  style = MaterialTheme.typography.titleMedium,
                                  fontWeight = FontWeight.Bold
                              )
                              Text(
                                  text = nombreherram.joinToString(
                                      ", ",
                                      prefix = "(",
                                      postfix = ")"
                                  ),
                                  style = MaterialTheme.typography.bodyMedium,
                                  color = Color(0xFF07247A)
                              )
                          }
                      }

                      val investigadoresProyec by tablaintermediade_ProyectoInvestigador.getInvestigadoresPorProyecto(proyecto.id).collectAsState(emptyList())

                      val nombreinv = investigadoresProyec.mapNotNull { investigadores ->

                          val nombreState =
                              investigadoresViewModel.getInvestigadorById(investigadores!!.investigadorId).collectAsState(null)


                          val nombre = nombreState.value?.nombre
                          nombre?.let {
                              if (investigadores.esPrincipal)
                                  "$it es Principal    "
                              else
                                  it
                          }

                      }


                      // Mini tarjeta para los Investigadores
                      Card(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(top = 8.dp),
                          shape = RoundedCornerShape(12.dp),
                          colors = CardDefaults.cardColors(containerColor = Color(0xFFA3D4F5)),
                          elevation = CardDefaults.cardElevation(4.dp)
                      ) {
                          Column(modifier = Modifier.padding(12.dp)) {
                              Text(
                                  text = "Investigadores del Proyecto ",
                                  style = MaterialTheme.typography.titleMedium,
                                  fontWeight = FontWeight.Bold
                              )
                              Text(
                                  text = nombreinv.joinToString(
                                      ", ",
                                      prefix = "(",
                                      postfix = ")"
                                  ),
                                  style = MaterialTheme.typography.bodyMedium,
                                  color = Color(0xFF060C06)
                              )
                          }
                      }

                  }

              }






            }







        }


        // Botón para añadir un investigador
        FloatingActionButton(
            onClick = {
                ver_form= true

                nombre = ""
                descripcion = ""
                fecha_inicio = ""
                fecha_fin = ""
                id_unico = ""

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
fun Greeting_Proyecto_screen() {

    Proyecto_screen(navController = NavController(LocalContext.current), appContainer = App_Container(LocalContext.current))
}