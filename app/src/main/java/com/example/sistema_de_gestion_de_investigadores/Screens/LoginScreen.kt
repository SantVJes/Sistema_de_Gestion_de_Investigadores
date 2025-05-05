@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sistema_de_gestion_de_investigadores.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sistema_de_gestion_de_investigadores.Data_Base.App_Container
import com.example.sistema_de_gestion_de_investigadores.ui.theme.Sistema_de_Gestion_de_InvestigadoresTheme
import com.example.sistema_de_gestion_de_investigadores.ui.theme.userViewModel

@Composable
fun login_user(navController: NavController, appContainer: App_Container) {
    Scaffold() { it

        Body_login(navController, appContainer)
    }

}

@Composable
fun Body_login(navController: NavController, appContainer: App_Container) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val basedata = App_Container(context)
    val  userViewModel = userViewModel(basedata.provideUsuarioRepository())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 28.sp, // Ajusta el tamaño de fuente aquí
                    fontWeight = FontWeight.Bold // Opcional: para que se vea más destacado
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.
            fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Aquí podrías validar el login con appContainer
                Toast.makeText(context, "Iniciando sesión...", Toast.LENGTH_SHORT).show()

                val permitido = userViewModel.login(email, password)

                if (permitido != null) {
                    Toast.makeText(context, "Bienvenido ", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Greeting_Login_user() {
    val localContext = LocalContext.current
    val dummyNavController = rememberNavController()
    val appContainer = App_Container(localContext)
    login_user(dummyNavController, appContainer)
}
