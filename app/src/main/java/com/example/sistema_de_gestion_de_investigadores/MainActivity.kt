package com.example.sistema_de_gestion_de_investigadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Usuario
import com.example.sistema_de_gestion_de_investigadores.ui.theme.Sistema_de_Gestion_de_InvestigadoresTheme
import androidx.lifecycle.viewModelScope
import com.example.sistema_de_gestion_de_investigadores.Data_Base.UsuariosRepository
import com.example.sistema_de_gestion_de_investigadores.Navigation.AppNavigations
import com.example.sistema_de_gestion_de_investigadores.ui.theme.userViewModel

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
