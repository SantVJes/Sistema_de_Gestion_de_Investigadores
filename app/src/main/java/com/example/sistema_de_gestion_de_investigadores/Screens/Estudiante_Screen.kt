package com.example.sistema_de_gestion_de_investigadores.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container


@Composable
fun Estudiantes_Screen(navController: NavController, appContainer: App_Container) {

}


@Composable
fun Body_estudiantes(navController: NavController, appContainer: App_Container) {

}


@Preview(showBackground = true)
@Composable
fun Estudiantes_ScreenPreview() {

    Estudiantes_Screen(navController = NavController(LocalContext.current), appContainer = App_Container(LocalContext.current))
}