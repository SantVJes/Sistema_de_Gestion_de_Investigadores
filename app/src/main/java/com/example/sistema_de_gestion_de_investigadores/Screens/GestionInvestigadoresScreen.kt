@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.ui.theme.AreaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ArticuloViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.InvestigadorLineaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.LineaTrabajoViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectoInvestigadorViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.ProyectosViewModel
import com.example.sistema_de_gestion_de_investigadores.ui.theme.investigadorViewModel
import java.lang.invoke.MethodHandles.empty

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
        }
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
    val investigadoresViewModel = investigadorViewModel(appContainer.provideInvestigadorRepository())

    // Recolectamos el Flow en un State observable
    val allInvestigadores by investigadoresViewModel.investigadores.collectAsState(emptyList())

    // Estado para controlar la edición de un investigador
    var investigadorEditado by remember { mutableStateOf<Investigador?>(null) }
    val areaTrabajoViewModel = AreaTrabajoViewModel(appContainer.provideAreaTrabajoRepository())
    val allAreasTrabajo by areaTrabajoViewModel.getAllAreasTrabajo().collectAsState(emptyList())
    val proyectoViewModel = ProyectosViewModel(appContainer.provideProyectoRepository())
    val articulosViewModel = ArticuloViewModel(appContainer.provideArticuloRepository())
    val tablaintermediade_ArticuloInvestigador = ArticuloInvestigadorViewModel(appContainer.provideArticuloInvestigadorRepository())
    val tablaintermediade_ProyectoInvestigador = ProyectoInvestigadorViewModel(appContainer.provideProyectoInvestigadorRepository())
    val lineaTrabajoViewModel = LineaTrabajoViewModel(appContainer.provideLineaTrabajoRepository())
    val tablaintermediade_InvestigadorLineaTrabajo = InvestigadorLineaTrabajoViewModel(appContainer.provideInvestigadorLineaTrabajoRepository())


    // Variables para el formulario de agregar/editar investigador
    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var areaId by remember { mutableStateOf("") }
    var nivelSNII by remember { mutableStateOf<String?>(null) }
    var fechaSNII by remember { mutableStateOf("") }
    var mostrarFormulario by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (mostrarFormulario) {


                // Formulario para agregar o editar investigador
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = especialidad,
                        onValueChange = { especialidad = it },
                        label = { Text("Especialidad") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = carrera,
                        onValueChange = { carrera = it },
                        label = { Text("Carrera") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = areaId,
                        onValueChange = { areaId = it },
                        label = { Text("Área") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = nivelSNII ?: "",
                        onValueChange = { nivelSNII = if (it.isNotEmpty()) it else null },
                        label = { Text("Nivel SNII") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = fechaSNII,
                        onValueChange = { fechaSNII = it },
                        label = { Text("Fecha SNII") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),

                    ) {
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = {
                                if (investigadorEditado == null) {
                                    // Si estamos añadiendo un investigador nuevo
                                    val nuevoInvestigador = Investigador(
                                        id = 0, // O cualquier valor que se maneje para el nuevo investigador
                                        nombre = nombre,
                                        especialidad = especialidad,
                                        carrera = carrera,
                                        areaId = areaId.toInt(),
                                        nivelSNII = nivelSNII,
                                        fechaSNII = fechaSNII
                                    )
                                    investigadoresViewModel.incertInvestigador(nuevoInvestigador)
                                } else {
                                    // Si estamos editando un investigador existente
                                    val investigadorEditado = investigadorEditado!!.copy(
                                        nombre = nombre,
                                        especialidad = especialidad,
                                        carrera = carrera,
                                        areaId = areaId.toInt(),
                                        nivelSNII = nivelSNII,
                                        fechaSNII = fechaSNII
                                    )
                                    investigadoresViewModel.updateInvestigador(investigadorEditado)
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
                                // Para que se cierre el teclado
                            },
                            modifier = Modifier.width(250.dp)
                        ) {
                            Text(if (investigadorEditado == null) "Añadir Investigador" else "Editar Investigador")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            shape = RectangleShape ,


                            onClick = {
                                mostrarFormulario = false

                            }
                        ){
                            Icon(Icons.Default.Close, contentDescription = "Buscar Investigador")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Usamos la lista recolectada por collectAsState
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(allInvestigadores) { investigador ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = investigador.nombre,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Especialidad: ${investigador.especialidad}")
                            Text("Carrera: ${investigador.carrera}")
                            if (investigador.nivelSNII != null) {
                                Text("Nivel SNII: ${investigador.nivelSNII}")
                                Text("Fecha SNII: ${investigador.fechaSNII}")
                            } else {
                                Text("Nivel SNII: No Tiene")
                            }
                            val area by areaTrabajoViewModel.getAreaTrabajoById(investigador.areaId).collectAsState(null)

                            Text("Área: ${area?.nombre}")

                            val lineas by tablaintermediade_InvestigadorLineaTrabajo.getLineasPorInvestigador(investigador.id).collectAsState(emptyList())
                            val nombresLineas = mutableListOf<String>()

                            for (linea in lineas) {
                                val obtenerNombre = lineaTrabajoViewModel.getLineaTrabajoById(linea.lineaTrabajoId).collectAsState(null)
                                nombresLineas.add(obtenerNombre.value?.nombre ?: "")

                            }

                            Text("Líneas de Trabajo: ${nombresLineas.joinToString(", ", prefix = "(", postfix = ")")}")
                            val proyectos by tablaintermediade_ProyectoInvestigador.getProyectoPorInvestigador(investigador.id).collectAsState(emptyList())
                            val nombresCompletos = proyectos.mapNotNull { proyecto ->
                                val nombreState = proyectoViewModel.getProyectoById(proyecto!!.proyectoId.toInt()).collectAsState(initial = null)
                                val nombre = nombreState.value?.nombre

                                nombre?.let {
                                    if (proyecto!!.esPrincipal)
                                        "$it es Principal "
                                    else
                                        it
                                }
                            }


                            Text("Proyectos: ${nombresCompletos.joinToString(", ", prefix = " ", postfix = "")}")

                            val articulos by tablaintermediade_ArticuloInvestigador.getArticuloPorInvestigador(investigador.id).collectAsState(emptyList())

                            val nombresArticulos = articulos.mapNotNull { articulo ->
                                val nombreState = articulosViewModel.getArticuloById(articulo!!.articuloId.toInt()).collectAsState(initial = null)
                                nombreState.value?.nombre
                                val nombre = nombreState.value?.nombre

                                nombre?.let {
                                    if (articulo!!.esPrincipal)
                                        "$it es Principal author  "
                                    else
                                        it
                                }
                            }
                            Text("Artículos: ${nombresArticulos.joinToString(", ", prefix = " ", postfix = "")}")

                            // Botón de eliminar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        investigadoresViewModel.deleteInvestigador(investigador)



                                    }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Investigador", tint = Color.Red)
                                }

                                // Botón de editar
                                IconButton(
                                    onClick = {
                                        mostrarFormulario = true
                                        // Cargar los datos del investigador en el formulario
                                        investigadorEditado = investigador
                                        nombre = investigador.nombre
                                        especialidad = investigador.especialidad
                                        carrera = investigador.carrera
                                        areaId = investigador.areaId.toString()
                                        nivelSNII = investigador.nivelSNII
                                        fechaSNII = investigador.fechaSNII ?: ""
                                    }
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar Investigador", tint = Color.Blue)
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
            },
            containerColor = Color(0xFF00BCD4),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir Investigador")
        }

    }

}


@Preview(showBackground = true)
@Composable
fun Greeting_investigadores() {
    Investigadores_Screen(NavController(androidx.compose.ui.platform.LocalContext.current), App_Container(androidx.compose.ui.platform.LocalContext.current))
}



