package com.example.sistema_de_gestion_de_investigadores.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.InvestigadoresRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Usuario
import com.example.sistema_de_gestion_de_investigadores.Data_Base.UsuariosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



class userViewModel(private val userRepository: UsuariosRepository) : ViewModel() {

    fun incertUser(user: Usuario){
        viewModelScope.launch{
            userRepository.addUsuario(user)
        }

    }

}

