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
import com.example.sistema_de_gestion_de_investigadores.ui.theme.userViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema_de_Gestion_de_InvestigadoresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    App_Container(context = this)

                    //incertar
                    val appContainer = App_Container(context = this)
                    val usuarioRepository = appContainer.provideUsuarioRepository()

                    val newuser = Usuario(
                        username = "test",
                        password = "test"
                    )
                    // Insert a new user (Corrected)

                    val viewModel = userViewModel(userRepository = usuarioRepository)

                    viewModel.incertUser( newuser)





                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Sistema_de_Gestion_de_InvestigadoresTheme {
        Greeting("Android")
    }
}