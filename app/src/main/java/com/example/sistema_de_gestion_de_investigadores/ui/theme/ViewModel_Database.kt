package com.example.sistema_de_gestion_de_investigadores.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_de_gestion_de_investigadores.Data_Base.AreaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.AreasTrabajoRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Articulo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ArticuloInvestigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ArticuloInvestigadorRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ArticuloRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Estudiante
import com.example.sistema_de_gestion_de_investigadores.Data_Base.EstudiantesRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Evento
import com.example.sistema_de_gestion_de_investigadores.Data_Base.EventosRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Herramienta
import com.example.sistema_de_gestion_de_investigadores.Data_Base.HerramientaRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Investigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.InvestigadorLineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.InvestigadorLineaTrabajoRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.InvestigadoresRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.LineaTrabajo
import com.example.sistema_de_gestion_de_investigadores.Data_Base.LineasTrabajoRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Proyecto
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoHerramienta
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoHerramientaRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoInvestigador
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectoInvestigadorRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.ProyectosRepository
import com.example.sistema_de_gestion_de_investigadores.Data_Base.Usuario
import com.example.sistema_de_gestion_de_investigadores.Data_Base.UsuariosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


//ViewModel de los usuarios
class userViewModel(private val userRepository: UsuariosRepository) : ViewModel() {

    fun login(username: String, password: String): Flow<Boolean?> {
        return userRepository.login(username, password)
    }


    fun incertUser(user: Usuario) {
        viewModelScope.launch {
            userRepository.addUsuario(user)
        }

    }

    fun updateUser(user: Usuario) {
        viewModelScope.launch {
            userRepository.updateUsuario(user)
        }
    }

    fun deleteUser(user: Usuario) {
        viewModelScope.launch {
            userRepository.deleteUsuario(user)
        }
    }
}

//ViewModel de los investigadores
class investigadorViewModel(private val investigadorRepository: InvestigadoresRepository) :
    ViewModel() {

    val investigadores: Flow<List<Investigador>> = investigadorRepository.getAllInvestigadores()


    fun getInvestigadorById(id: Int): Flow<Investigador?> {
        return investigadorRepository.getInvestigadorById(id)
    }

    fun incertInvestigador(investigador: Investigador) {
        viewModelScope.launch {
            investigadorRepository.addInvestigador(investigador)
        }
    }

    fun updateInvestigador(investigador: Investigador) {
        viewModelScope.launch {
            investigadorRepository.updateInvestigador(investigador)
        }
    }

    fun deleteInvestigador(investigador: Investigador) {
        viewModelScope.launch {
            investigadorRepository.deleteInvestigador(investigador)

        }
    }


}

//ViewModel de las Areas de trabajo

class AreaTrabajoViewModel(private val areaTrabajoRepository: AreasTrabajoRepository) :
    ViewModel() {

    fun getAllAreasTrabajo(): Flow<List<AreaTrabajo>> {
        return areaTrabajoRepository.getAllAreasTrabajo()
    }

    fun getAreaTrabajoById(id: Int): Flow<AreaTrabajo?> {
        return areaTrabajoRepository.getAreaTrabajoById(id)
    }

    fun getAreaTrabajoByNombre(nombre: String): Flow<AreaTrabajo?> {
        return areaTrabajoRepository.getAreaTrabajoByNombre(nombre)
    }

    fun incertAreaTrabajo(areaTrabajo: AreaTrabajo) {
        viewModelScope.launch {
            areaTrabajoRepository.addAreaTrabajo(areaTrabajo)
        }
    }

    fun updateAreaTrabajo(areaTrabajo: AreaTrabajo) {
        viewModelScope.launch {
            areaTrabajoRepository.updateAreaTrabajo(areaTrabajo)
        }
    }

    fun deleteAreaTrabajo(areaTrabajo: AreaTrabajo) {
        viewModelScope.launch {
            areaTrabajoRepository.deleteAreaTrabajo(areaTrabajo)
        }
    }
}

//ViewModel Lineas de trabajo

class LineaTrabajoViewModel(private val lineaTrabajoRepository: LineasTrabajoRepository) :
    ViewModel() {
    fun getAllLineasTrabajo(): Flow<List<LineaTrabajo>> {
        return lineaTrabajoRepository.getAllLineasTrabajo()
    }

    fun getLineaTrabajoById(id: Int): Flow<LineaTrabajo?> {
        return lineaTrabajoRepository.getLineaTrabajoById(id)
    }

    fun incertLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        viewModelScope.launch {
            lineaTrabajoRepository.addLineaTrabajo(lineaTrabajo)
        }
    }

    fun updateLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        viewModelScope.launch {
            lineaTrabajoRepository.updateLineaTrabajo(lineaTrabajo)
        }
    }

    fun deleteLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        viewModelScope.launch {
            lineaTrabajoRepository.deleteLineaTrabajo(lineaTrabajo)
        }
    }

}

//View Model de la tabla intermedia de InvestigadorLineaTrabajo

class InvestigadorLineaTrabajoViewModel(private val investigadorLineaTrabajoRepository: InvestigadorLineaTrabajoRepository) :
    ViewModel() {

    fun getLineasPorInvestigador(investigadorId: Int): Flow<List<InvestigadorLineaTrabajo>> {
        return investigadorLineaTrabajoRepository.getLineasPorInvestigador(investigadorId)
    }

    fun incertRelacion(relacion: InvestigadorLineaTrabajo) {
        viewModelScope.launch {
            investigadorLineaTrabajoRepository.addRelacion(relacion)
        }
    }

    fun deleteRelacion(relacion: InvestigadorLineaTrabajo) {
        viewModelScope.launch {
            investigadorLineaTrabajoRepository.deleteRelacion(relacion)
        }
    }
}

//Estudiantes ViewModel
class EstudiantesViewModel(private val estudiantesRepository: EstudiantesRepository) : ViewModel() {
    fun getAllEstudiantes(): Flow<List<Estudiante>> {
        return estudiantesRepository.getAllEstudiantes()
    }

    fun getAllEstudianteById(investigadorId: Int): Flow<List<Estudiante?>> {
        return estudiantesRepository.getAllEstudianteById(investigadorId)
    }

    fun incertEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            estudiantesRepository.addEstudiante(estudiante)
        }
    }

    fun deleteEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            estudiantesRepository.deleteEstudiante(estudiante)
        }
    }
}

//ViewModel de los proyectos
class ProyectosViewModel(private val proyectosRepository: ProyectosRepository) : ViewModel() {


    val proyectos: Flow<List<Proyecto>> = proyectosRepository.getAllProyectos()


    fun getProyectoById(id: Int): Flow<Proyecto?> {
        return proyectosRepository.getProyectoById(id)
    }

    fun incertProyecto(proyecto: Proyecto) {
        viewModelScope.launch {
            proyectosRepository.addProyecto(proyecto)
        }
    }

    fun deleteProyecto(proyecto: Proyecto) {
        viewModelScope.launch {
            proyectosRepository.deleteProyecto(proyecto)
        }
    }

    fun updateProyecto(proyecto: Proyecto) {
        viewModelScope.launch {
            proyectosRepository.updateProyecto(proyecto)
        }
    }

}

//ViewModel de las herramientas
class HerramientaViewModel(private val herramientaRepository: HerramientaRepository) : ViewModel() {
    val herramientas: Flow<List<Herramienta>> = herramientaRepository.getAllHerramientas()

    fun getAllHerramientas(): Flow<List<Herramienta>> {
        return herramientaRepository.getAllHerramientas()
    }

    fun getHerramientaById(id: Int): Flow<Herramienta?> {
        return herramientaRepository.getHerramientaById(id)

    }

    fun incertHerramienta(herramienta: Herramienta) {
        viewModelScope.launch {
            herramientaRepository.addHerramienta(herramienta)
        }
    }

    fun deleteHerramienta(herramienta: Herramienta) {
        viewModelScope.launch {
            herramientaRepository.deleteHerramienta(herramienta)
        }
    }

}

//ViewModel de la tabla intermedia de ProyectoHerramienta
class ProyectoHerramientaViewModel(private val proyectoHerramientaRepository: ProyectoHerramientaRepository) :
    ViewModel() {
    fun getHerramientasPorProyecto(proyectoId: Int): Flow<List<ProyectoHerramienta>> {
        return proyectoHerramientaRepository.getHerramientasPorProyecto(proyectoId)
    }

    fun incertRelacion(relacion: ProyectoHerramienta) {
        viewModelScope.launch {
            proyectoHerramientaRepository.addRelacion(relacion)
        }
    }

    fun deleteRelacion(relacion: ProyectoHerramienta) {
        viewModelScope.launch {
            proyectoHerramientaRepository.deleteRelacion(relacion)
        }
    }

}

//ViewModel de la tabla intermedia de ProyectoInvestigador

class ProyectoInvestigadorViewModel(private val proyectoInvestigadorRepository: ProyectoInvestigadorRepository) :
    ViewModel() {
    fun getInvestigadoresPorProyecto(proyectoId: Int): Flow<List<ProyectoInvestigador?>> {
        return proyectoInvestigadorRepository.getInvestigadoresPorProyecto(proyectoId)
    }

    fun getProyectoPorInvestigador(investigadorId: Int): Flow<List<ProyectoInvestigador?>> {
        return proyectoInvestigadorRepository.getProyectoPorInvestigador(investigadorId)
    }

    fun incertRelacion(relacion: ProyectoInvestigador) {
        viewModelScope.launch {
            proyectoInvestigadorRepository.addRelacion(relacion)
        }
    }

    fun deleteRelacion(relacion: ProyectoInvestigador) {
        viewModelScope.launch {
            proyectoInvestigadorRepository.deleteRelacion(relacion)
        }
    }


}

class ArticuloViewModel(private val articuloRepository: ArticuloRepository) : ViewModel() {
    fun getAllArticulos(): Flow<List<Articulo>> {
        return articuloRepository.getAllArticulos()
    }

    fun getArticuloById(id: Int): Flow<Articulo?> {
        return articuloRepository.getArticuloById(id)
    }

    fun incertArticulo(articulo: Articulo) {
        viewModelScope.launch {
            articuloRepository.addArticulo(articulo)
        }
    }

    fun updateArticulo(articulo: Articulo) {
        viewModelScope.launch {
            articuloRepository.updateArticulo(articulo)
        }
    }

    fun deleteArticulo(articulo: Articulo) {
        viewModelScope.launch {
            articuloRepository.deleteArticulo(articulo)
        }
    }
}

//ViewModel de la tabla intermedia de ArticuloInvestigador

class ArticuloInvestigadorViewModel(private val articuloInvestigadorRepository: ArticuloInvestigadorRepository) :
    ViewModel() {
    fun getInvestigadoresPorArticulo(articuloId: Int): Flow<List<ArticuloInvestigador>> {
        return articuloInvestigadorRepository.getInvestigadoresPorArticulo(articuloId)
    }

    fun getArticuloPorInvestigador(investigadorId: Int): Flow<List<ArticuloInvestigador?>> {
        return articuloInvestigadorRepository.getArticuloPorInvestigador(investigadorId)
    }

    fun incertRelacion(relacion: ArticuloInvestigador) {
        viewModelScope.launch {
            articuloInvestigadorRepository.addRelacion(relacion)
        }
    }

    fun deleteRelacion(relacion: ArticuloInvestigador) {
        viewModelScope.launch {
            articuloInvestigadorRepository.deleteRelacion(relacion)
        }
    }


}

//ViewModel de los eventos
class eventosViewModel(private val eventosRepository: EventosRepository) : ViewModel() {
    fun getAllEventos(): Flow<List<Evento>> {
        return eventosRepository.getAllEventos()
    }

    fun getEventoById(id: Int): Flow<Evento?> {
        return eventosRepository.getEventoById(id)
    }

    fun getEventoByInvestigador(investigadorId: Int): Flow<List<Evento?>> {
        return eventosRepository.getEventoByInvestigador(investigadorId)
    }

    fun incertEvento(evento: Evento) {
        viewModelScope.launch {
            eventosRepository.addEvento(evento)
        }
    }

    fun updateEvento(evento: Evento) {
        viewModelScope.launch {
            eventosRepository.updateEvento(evento)
        }
    }

    fun deleteEvento(evento: Evento) {
        viewModelScope.launch {
            eventosRepository.deleteEvento(evento)
        }
    }

}

