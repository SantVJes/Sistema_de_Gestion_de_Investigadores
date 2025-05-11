@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Evento
import com.example.sistema_de_gestion_de_investigadores.Data_Base.LineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.eventosViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration

@Composable
fun Eventos_Screen(navController: NavController, appContainer: App_Container) {
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
                            text = " Eventos Programados ",
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

            Body_eventos(navController, appContainer)
        }
    }
    
}

@Composable
fun Body_eventos(navController: NavController, appContainer: App_Container) {

    //variables de la base de datos
    val investigadoresViewModel =
        investigadorViewModel(appContainer.provideInvestigadorRepository())
    val allInvestigadores by investigadoresViewModel.investigadores.collectAsState(emptyList())
    val eventosViewModel = eventosViewModel(appContainer.provideEventoRepository())
    val allEventos by eventosViewModel.getAllEventos().collectAsState(emptyList())
    var evento_eli by remember { mutableStateOf<Evento?>(null) }

    //variables a utilizar
    var ver_fomr by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var fechaevento by remember { mutableStateOf("") }
    var nom_investigador by remember { mutableStateOf("") }
    var id_investigador by remember { mutableStateOf<Int?>(null) }
    var lugar by remember { mutableStateOf("") }

    var duracion by remember { mutableStateOf("") }
    var empresa by remember { mutableStateOf("") }
    var edit by remember { mutableStateOf(false) }
    var expand5 by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    var expanded by remember { mutableStateOf(false) }




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
                        label = { Text("Nombre del evento") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = lugar,
                        onValueChange = { lugar = it },
                        label = { Text("Lugar del evento") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Mostrar el DatePickerDialog al hacer clic
                    val datePickerDialog = remember {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                fechaevento = dateFormatter.format(calendar.time)
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
                                text = "Fecha del evento ",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (fechaevento.isNotEmpty()) fechaevento else "Selecciona una fecha del Evento",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (fechaevento.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { _, hour: Int, minute: Int ->
                            duracion = String.format("%02d:%02d", hour, minute)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true // formato 24h
                    )



                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePickerDialog.show() }
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .padding(8.dp) // Espaciado interno para parecerse a un TextField
                    ) {
                        Column {
                            Text(
                                text = "Duración del evento",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (duracion.isNotEmpty()) duracion else "Selecciona una hora",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (duracion.isNotEmpty()) Color.Black else Color.LightGray
                            )
                        }
                    }

                    OutlinedTextField(
                        value = empresa,
                        onValueChange = { empresa = it },
                        label = { Text("Empresa Invitada") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = nom_investigador ?: "No tiene",
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
                                        nom_investigador = opcion.nombre
                                        id_investigador = opcion.id
                                        expanded = false
                                    },
                                    text = { Text(opcion.nombre) }
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
                                val nextId = (allEventos.maxOfOrNull { it.id } ?: 0) + 1

                                if (edit == false) {
                                    val eventoNew = Evento(
                                        id = nextId,
                                        nombre = nombre,
                                        fecha = fechaevento,
                                        lugar = lugar,
                                        duracion = duracion,
                                        empresaInvita = empresa,
                                        investigadorId = id_investigador!!
                                    )
                                    eventosViewModel.incertEvento(eventoNew)
                                    //incertar relacion

                                } else {






                                }
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 25.dp)
                        ) {
                            Text(if (edit == false) "Añadir Evento" else "Editar Evento")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            shape = RectangleShape,

                            onClick = {
                                edit = false
                                nombre = ""
                                ver_fomr = false
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Salir form")
                        }
                    }



                }
            }

        }
    }
    
}


@Preview(showBackground = true)
@Composable
fun Greeting_eventos() {
    Eventos_Screen(navController = NavController(LocalContext.current), appContainer = App_Container(LocalContext.current))
}