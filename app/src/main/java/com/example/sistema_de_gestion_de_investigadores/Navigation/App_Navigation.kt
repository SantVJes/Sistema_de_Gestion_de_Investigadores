package com.example.sistema_de_gestion_de_investigadores.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Navigation.Directorio.lineas_trabajo
import com.example.sistema_de_gestion_de_investigadores.Screens.Herramientas_screen
import com.example.sistema_de_gestion_de_investigadores.Screens.Home_screen
import com.example.sistema_de_gestion_de_investigadores.Screens.Investigadores_Screen
import com.example.sistema_de_gestion_de_investigadores.Screens.login_user
import com.example.sistema_de_gestion_de_investigadores.Screens.Proyecto_screen
import com.example.sistema_de_gestion_de_investigadores.Screens.lineas_Screen


@Composable
fun AppNavigations(appContainer: App_Container) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Directorio.loginScreen
    ){
        composable(Directorio.loginScreen){
            login_user(navController, appContainer)
        }
        composable(Directorio.HomeScreen){
            Home_screen(navController, appContainer)
        }
        composable(Directorio.InvestigadoresScreen){
            Investigadores_Screen(navController, appContainer)
        }
        composable(Directorio.ProyectosScreen){
            Proyecto_screen(navController, appContainer)
        }
        composable(Directorio.herramientaScreen){
            Herramientas_screen(navController, appContainer)
        }
        composable(Directorio.lineas_trabajo){
            lineas_Screen(navController, appContainer)
        }

    }

}
