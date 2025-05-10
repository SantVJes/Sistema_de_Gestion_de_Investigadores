@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Herramienta
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.HerramientaViewModel


@Composable
fun Herramientas_screen(navController: NavHostController, appContainer: App_Container) {

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
                            text = " Herramientas ",
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

            Body_herramientas(navController, appContainer)
        }
    }
}


@Composable
fun Body_herramientas(navController: NavHostController, appContainer: App_Container) {

    //Estados para controlar la base de datos
    val herramientaViewModel = HerramientaViewModel(appContainer.provideHerramientaRepository())
    var expanded by remember { mutableStateOf(false) }

    //Variables a utilizar
    var ver_form by remember { mutableStateOf(true) }
    var edit by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    val opcionesTipo = listOf(
        "No tiene",
        "Capacitacion",
        "Investigacion",
        "Publicacion",
        "Patente",
        "Software",
        "Hadware"
    )

    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier.padding(16.dp)) {


            if (ver_form) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre de la Herramienta") },
                        modifier = Modifier.fillMaxWidth()
                    )





                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = tipo,
                            onValueChange = {},
                            label = { Text("Tipo de Herramienta") },
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
                            opcionesTipo.forEach { opcion ->
                                DropdownMenuItem(
                                    onClick = {
                                        tipo =
                                            (if (opcion == "No tiene") null else opcion).toString()
                                        expanded = false
                                    },
                                    text = { Text(opcion) }
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

                                if (edit == false) {


                                    val herramientaNew = Herramienta(
                                        nombre = nombre,
                                        tipo = tipo
                                    )
                                    herramientaViewModel.incertHerramienta(herramientaNew)




                                    nombre = ""
                                    tipo = ""

                                    ver_form = false


                                } else {


                                    val herramientaEdit = Herramienta(
                                        id = id.toInt(),
                                        nombre = nombre,
                                        tipo = tipo
                                    )












                                    nombre = ""
                                    tipo = ""

                                    ver_form = false


                                }









                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 25.dp)
                        ) {
                            Text(if (edit == false) "Añadir Herramienta" else "Editar Herramienta")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            shape = RectangleShape,

                            onClick = {
                                nombre = ""
                                tipo = ""
                                ver_form = false
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Salir form")
                        }
                    }


                }


            }







        }
        // Botón para añadir un investigador
        FloatingActionButton(
            onClick = {
                ver_form = true
                edit = false
                nombre = ""
                tipo = ""


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
fun Greeting_herramientas() {
    Herramientas_screen(rememberNavController(), App_Container(LocalContext.current))
}



