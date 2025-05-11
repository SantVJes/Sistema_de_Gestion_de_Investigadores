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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.DatePickerDefaults.dateFormatter
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Estudiante
import com.example.sistema_de_gestion_de_investigadores.Data_Base.LineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.EstudiantesViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun Estudiantes_Screen(navController: NavController, appContainer: App_Container) {

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
                            text = " Lista de Estudiantes ",
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

            Body_estudiantes(navController, appContainer)
        }
    }
}


@Composable
fun Body_estudiantes(navController: NavController, appContainer: App_Container) {

    //variables de base de datos
    val estudianteViewModel = EstudiantesViewModel(appContainer.provideEstudianteRepository())
    val allEstudiantes by estudianteViewModel.getAllEstudiantes().collectAsState(emptyList())
    val investigadorViewModel = investigadorViewModel(appContainer.provideInvestigadorRepository())
    val allInvestigadores by investigadorViewModel.investigadores.collectAsState(emptyList())

    //Variables a utilizar
    var ver_fomr by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var escuela by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var investigadorId: Int? by remember { mutableStateOf<Int?>(null) }
    var nombreinvestigador by remember { mutableStateOf("") }
    var edit by remember { mutableStateOf(false) }
    val listTipo = listOf("Practicante", "Servicio Social","Maestría", "Doctorado", "Especialización")
    var expanded by remember { mutableStateOf(false) }
    var expanded1 by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier.padding(16.dp)) {
            if (ver_fomr) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del estudiante") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded } // alterna al hacer clic
                    ) {
                        OutlinedTextField(
                            value = tipo ?: "Selecciona el Tipo de estudiante",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de estudiante") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor() // importante para alinear bien el menú
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listTipo.forEach { tipos ->
                                DropdownMenuItem(
                                    text = { Text(tipos) },
                                    onClick = {
                                        tipo = tipos
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = carrera,
                        onValueChange = { carrera = it },
                        label = { Text("Carrera del estudiante") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = escuela,
                        onValueChange = { escuela = it },
                        label = { Text("Escuela del estudiante") },
                        modifier = Modifier.fillMaxWidth()
                    )


                    // Mostrar el DatePickerDialog al hacer clic
                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                fechaInicio = dateFormatter.format(calendar.time)
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
                                fechaFin = dateFormatter.format(calendar.time)
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
                                text = if (fechaInicio.isNotEmpty()) fechaInicio else "Selecciona una fecha de Inicio",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fechaInicio.isNotEmpty()) Color.Black else Color.LightGray
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
                                text = if (fechaFin.isNotEmpty()) fechaFin else "Selecciona una fecha Finalizacion",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fechaFin.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = expanded1,
                        onExpandedChange = { expanded1 = !expanded1 } // alterna al hacer clic
                    ) {
                        OutlinedTextField(
                            value = nombreinvestigador ?: "Selecciona El Investigador a cargo",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Investigador a cargo") },
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
                            allInvestigadores.forEach { inv ->
                                DropdownMenuItem(
                                    text = { Text(inv.nombre) },
                                    onClick = {
                                        investigadorId = inv.id
                                        nombreinvestigador = inv.nombre
                                        expanded = false
                                    }
                                )
                            }
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

                            if (edit == false) {

                                val estudianteNew = Estudiante(
                                    nombre = nombre,
                                    tipo = tipo,
                                    carrera = carrera,
                                    escuela = escuela,
                                    fechaInicio = fechaInicio,
                                    fechaFin = fechaFin,
                                    investigadorId = investigadorId!!
                                )
                                estudianteViewModel.incertEstudiante(estudianteNew)

                                nombre = ""
                                edit = false
                                id = ""
                                ver_fomr = false
                                tipo = ""
                                carrera = ""
                                escuela = ""
                                fechaInicio = ""
                                fechaFin = ""


                            } else {


                                nombre = ""
                                edit = false
                                id = ""
                                ver_fomr = false
                                tipo = ""
                                carrera = ""
                                escuela = ""
                                fechaInicio = ""
                                fechaFin = ""


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

                            nombreinvestigador = ""
                            fechaInicio = ""
                            fechaFin = ""
                            tipo = ""
                            carrera = ""
                            escuela = ""



                            ver_fomr = false
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Salir form")
                    }
                }
            }



            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(allEstudiantes) { estudiante ->

                    val investigadorMaestro =
                        investigadorViewModel.getInvestigadorById(estudiante.investigadorId)
                            .collectAsState(null)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            // Nombre con ícono
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Estudiante",
                                    tint = Color(0xFF1565C0),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = estudiante.nombre,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1565C0)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Detalles académicos
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Tipo: ${estudiante.tipo}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Carrera: ${estudiante.carrera}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Escuela: ${estudiante.escuela}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Fechas en fila
                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "Inicio: ${estudiante.fechaInicio}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Fin: ${estudiante.fechaFin}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Investigador
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4C3))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Investigador a cargo",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF33691E)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = investigadorMaestro.value?.nombre ?: "No asignado",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF558B2F)
                                    )
                                }


                            }
                        }
                        // Botones de eliminar y editar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, end = 14.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    estudianteViewModel.deleteEstudiante(estudiante)
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
                                    ver_fomr = true
                                    edit = true
                                    id = estudiante.id.toString()
                                    nombre = estudiante.nombre
                                    tipo = estudiante.tipo
                                    carrera = estudiante.carrera
                                    escuela = estudiante.escuela
                                    fechaInicio = estudiante.fechaInicio
                                    fechaFin = estudiante.fechaFin
                                    investigadorId = estudiante.investigadorId
                                    nombreinvestigador =
                                        investigadorMaestro.value?.nombre ?: "No asignado"


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

}


@Preview(showBackground = true)
@Composable
fun Estudiantes_ScreenPreview() {

    Estudiantes_Screen(navController = NavController(LocalContext.current), appContainer = App_Container(LocalContext.current))
}