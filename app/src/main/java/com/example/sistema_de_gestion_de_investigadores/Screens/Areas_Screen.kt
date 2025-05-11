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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.AreaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.LineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio
import com.example.sistema_de_gestion_de_investigadores.ui.theme.AreaTrabajoViewModel


@Composable
fun Areas_Screen(navController: NavController, appContainer: App_Container) {

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
                            text = " Areas De Trabajo ",
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

            Body_Areas(navController, appContainer)
        }
    }
}


@Composable
fun Body_Areas(navController: NavController, appContainer: App_Container) {

    //Variables a utilizar
    var ver_form by remember { mutableStateOf(false) }
    var edit by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }

    var edi by remember { mutableStateOf("") }
    var area_er by remember { mutableStateOf<AreaTrabajo?>(null) }

    var jefearea by remember { mutableStateOf("") }
    val lugar = remember {  mutableStateListOf(
        "Ciudad Guadalajara",
        "Ciudad Monterrey",
        "Ciudad de México",
        "Ciudad Puebla",
        "Ciudad Querétaro",
        "Unidad 1",
        "Unidad 2",
        "Unidad 3",
        "Unidad 4",
        "Unidad 5"
    )}
    var selclugar by remember { mutableStateOf("") }
    //Estados para controlar la base de datos
    val areaTrabajoViewModel = AreaTrabajoViewModel(appContainer.provideAreaTrabajoRepository())
    val allAreasTrabajo by areaTrabajoViewModel.getAllAreasTrabajo().collectAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier.padding(16.dp)) {

            if(ver_form){

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del Area") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = jefearea,
                        onValueChange = { jefearea = it },
                        label = { Text("Nombre del jefe de area") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selclugar,
                            onValueChange = {},
                            label = { Text("Seleciona el lugar") },
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
                            lugar.forEach { opcion ->
                                DropdownMenuItem(
                                    onClick = {
                                        selclugar =
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

                                    val areaNew = AreaTrabajo(
                                        nombre = nombre,
                                        jefeArea = jefearea,
                                        lugar = selclugar
                                    )

                                    areaTrabajoViewModel.incertAreaTrabajo(areaNew)
                                    ver_form = false
                                    nombre = ""
                                    jefearea = ""
                                    selclugar = ""



                                }else{
                                    area_er?.nombre = nombre.toString()
                                    area_er?.jefeArea = jefearea.toString()
                                    area_er?.lugar = selclugar.toString()

                                    areaTrabajoViewModel.updateAreaTrabajo(area_er!!)

                                    ver_form = false
                                    edit = false
                                    nombre = ""
                                    jefearea = ""
                                    selclugar = ""


                                }


                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 25.dp)
                        ) {
                            Text(if (edit == false) "Añadir Area lugar" else "Editar Area lugar" )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            shape = RectangleShape,

                            onClick = {
                                ver_form = false
                                edi = ""

                                nombre = ""
                                jefearea = ""
                                selclugar = ""



                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Salir form")
                        }
                    }






                }




            }




            LazyColumn (verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp))
            {
                items(allAreasTrabajo) { index ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0)), // Azul más profundo y elegante
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            Text(
                                text = index.nombre,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 0.75.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = "Lugar",
                                    tint = Color.White.copy(alpha = 0.8f),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = index.lugar,
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontStyle = FontStyle.Italic
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Jefe de Área",
                                    tint = Color.White.copy(alpha = 0.8f),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = index.jefeArea,
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    // Botones de eliminar y editar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp,end = 14.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                areaTrabajoViewModel.deleteAreaTrabajo(index)
                            }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar area",
                                tint = Color.Red
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                edit = true
                                nombre = index.nombre
                                jefearea = index.jefeArea
                                selclugar = index.lugar
                                ver_form = true
                                area_er = index
                                edi= index.id.toString()



                            },

                            ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar area ",
                                tint = Color.Blue
                            )
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
                jefearea = ""
                selclugar = ""

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
fun Areas_ScreenPreview() {

    Areas_Screen(navController = NavController(LocalContext.current), appContainer = App_Container(LocalContext.current))
}