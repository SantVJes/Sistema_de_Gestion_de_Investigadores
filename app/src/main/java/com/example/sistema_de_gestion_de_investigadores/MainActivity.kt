package com.example.sistema_de_gestion_de_investigadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Navigation.AppNavigations
import com.example.sistema_de_gestion_de_investigadores.ui.theme.Sistema_de_Gestion_de_InvestigadoresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema_de_Gestion_de_InvestigadoresTheme {
                AppNavigations(appContainer = App_Container(this))
            }
        }
    }
}
