package com.example.sistema_de_gestion_de_investigadores.Data_Base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



//Interfaces de las tablas de la base de datos

/*
Repositorio de usuarios que proporciona ,login, agregar, actualizar y eliminar usuarios
 */
interface UsuariosRepository{
    fun login(username: String, password: String): Flow<Boolean?>
    suspend fun addUsuario(usuario: Usuario)
    suspend fun updateUsuario(usuario: Usuario)
    suspend fun deleteUsuario(usuario: Usuario)
}

/*
Repositorio de investigadores que proporciona obtener a todos, obtener por id, agregar, actualizar y eliminar investigadores
 */
interface InvestigadoresRepository{
    fun getAllInvestigadores(): Flow<List<Investigador>>
    fun getInvestigadorById(id: Int): Flow<Investigador?>
    suspend fun addInvestigador(investigador: Investigador)
    suspend fun updateInvestigador(investigador: Investigador)
    suspend fun deleteInvestigador(investigador: Investigador)
}
/*
    Repositorio de Areas de trabajo que proporciona obtener a todas, obtener por id o nombre, agregar, actualizar y eliminar areas de trabajo
 */
interface AreasTrabajoRepository{
    fun getAllAreasTrabajo(): Flow<List<AreaTrabajo>>
    fun getAreaTrabajoById(id: Int): Flow<AreaTrabajo?>
    fun getAreaTrabajoByNombre(nombre: String): Flow<AreaTrabajo?>
    suspend fun addAreaTrabajo(areaTrabajo: AreaTrabajo)
    suspend fun updateAreaTrabajo(areaTrabajo: AreaTrabajo)
    suspend fun deleteAreaTrabajo(areaTrabajo: AreaTrabajo)

}
/*
Repositorio de Lineas de trabajo que proporciona obtener a todas, obtener por id, agregar, actualizar y eliminar lineas de trabajo
 */
interface LineasTrabajoRepository{
    fun getAllLineasTrabajo(): Flow<List<LineaTrabajo>>
    fun getLineaTrabajoById(id: Int): Flow<LineaTrabajo?>
    suspend fun addLineaTrabajo(lineaTrabajo: LineaTrabajo)
    suspend fun updateLineaTrabajo(lineaTrabajo: LineaTrabajo)
    suspend fun deleteLineaTrabajo(lineaTrabajo: LineaTrabajo)
}
/*
  Repositorio de la tabla intermedia de InvestigadorLiniaTrabajo que proporciona obtener lineas de trabajo por
  investigador, agregar, y eliminar lineas de trabajo por investigador
 */
interface InvestigadorLineaTrabajoRepository {

    fun getLineasPorInvestigador(investigadorId: Int): Flow<List<InvestigadorLineaTrabajo>>

    suspend fun addRelacion(relacion: InvestigadorLineaTrabajo)

    suspend fun deleteRelacion(relacion: InvestigadorLineaTrabajo)
}
/*
Repositorio de estudiantes que proporciona obtener a todos, obtener por id de su investigador, agregar, actualizar y eliminar estudiantes
 */
interface EstudiantesRepository {
    fun getAllEstudiantes(): Flow<List<Estudiante>>
    fun getAllEstudianteById(investigadorId: Int): Flow<List<Estudiante?>>
    suspend fun addEstudiante(estudiante: Estudiante)
    suspend fun deleteEstudiante(estudiante: Estudiante)

}
/*
Repositorio de proyectos que proporciona obtener a todos, obtener por id, agregar, actualizar y eliminar proyectos
 */

interface ProyectosRepository {
    fun getAllProyectos(): Flow<List<Proyecto>>
    fun getProyectoById(id: Int): Flow<Proyecto?>
    suspend fun addProyecto(proyecto: Proyecto)
    suspend fun updateProyecto(proyecto: Proyecto)
    suspend fun deleteProyecto(proyecto: Proyecto)
}

/*
 Repositorio de herramientas que proporciona  obtener por id, agregar  y eliminar herramientas
 */
interface HerramientaRepository{
    fun getAllHerramientas(): Flow<List<Herramienta>>
    fun getHerramientaById(id: Int): Flow<Herramienta?>
    suspend fun addHerramienta(herramienta: Herramienta)
    suspend fun deleteHerramienta(herramienta: Herramienta)
}


/*
Repositorio de la tabla intermedia de ProyectoHerramienta que proporciona obtener herramientas
por proyecto, agregar y eliminar herramientas por proyecto
 */
interface ProyectoHerramientaRepository {
    fun getHerramientasPorProyecto(proyectoId: Int): Flow<List<ProyectoHerramienta>>
    suspend fun addRelacion(relacion: ProyectoHerramienta)
    suspend fun deleteRelacion(relacion: ProyectoHerramienta)
}

/*
Repositorio de la tabla intermedia de ProyectoInvestigador que proporciona obtener investigadores
por proyecto, agregar y eliminar investigadores por proyecto
 */

interface ProyectoInvestigadorRepository {
    fun getInvestigadoresPorProyecto(proyectoId: Int): Flow<List<ProyectoInvestigador?>>
    fun getProyectoPorInvestigador(investigadorId: Int): Flow<List<ProyectoInvestigador?>>

    suspend fun addRelacion(relacion: ProyectoInvestigador)
    suspend fun deleteRelacion(relacion: ProyectoInvestigador)

}

/*
Repositorio de articulos que proporciona obtener a todos, obtener por id, agregar, actualizar y eliminar articulos
 */
interface ArticuloRepository{
    fun getAllArticulos(): Flow<List<Articulo>>
    fun getArticuloById(id: Int): Flow<Articulo?>
    suspend fun addArticulo(articulo: Articulo)
    suspend fun updateArticulo(articulo: Articulo)
    suspend fun deleteArticulo(articulo: Articulo)
}

/*
Repositorio de la tabla intermedia de ArticuloInvestigador que proporciona obtener investigadores
por articulo, agregar y eliminar investigadores por articulo
 */
interface ArticuloInvestigadorRepository {
    fun getInvestigadoresPorArticulo(articuloId: Int): Flow<List<ArticuloInvestigador>>
    fun getArticuloPorInvestigador(investigadorId: Int): Flow<ArticuloInvestigador?>
    suspend fun addRelacion(relacion: ArticuloInvestigador)
    suspend fun deleteRelacion(relacion: ArticuloInvestigador)
}

/*
Repositorio de eventos que proporciona obtener a todos, obtener por id, agregar, actualizar y eliminar eventos

 */

interface EventosRepository{
    fun getAllEventos(): Flow<List<Evento>>
    fun getEventoById(id: Int): Flow<Evento?>
    fun getEventoByInvestigador(investigadorId: Int): Flow<List<Evento?>>
    suspend fun addEvento(evento: Evento)
    suspend fun updateEvento(evento: Evento)
    suspend fun deleteEvento(evento: Evento)
}

/*
llamado de los repositorios
 */

class UsuarioRepositoryImple(private val userDao: UsuariosDao) : UsuariosRepository {

    override fun login(username: String, password: String): Flow<Boolean> = flow {
        val usuario = userDao.login(username, password)
        emit(usuario != null)
    }

    override suspend fun addUsuario(usuario: Usuario) {
        userDao.addUsuario(usuario)
    }
    override suspend fun updateUsuario(usuario: Usuario) {
        userDao.updateUsuario(usuario)
    }
    override suspend fun deleteUsuario(usuario: Usuario) {
        userDao.deleteUsuario(usuario)
    }

}

class InvestigadorRepositoryImple(private val investigadorDao: InvestigadoresDao) : InvestigadoresRepository {

    override fun getAllInvestigadores(): Flow<List<Investigador>> = flow {
        emit(investigadorDao.getAllInvestigadores())
    }
    override fun getInvestigadorById(id: Int): Flow<Investigador?> = flow {
        emit(investigadorDao.getInvestigadorById(id))
    }
    override suspend fun addInvestigador(investigador: Investigador) {
        investigadorDao.addInvestigador(investigador)
    }
    override suspend fun updateInvestigador(investigador: Investigador) {
        investigadorDao.updateInvestigador(investigador)
    }
    override suspend fun deleteInvestigador(investigador: Investigador) {
        investigadorDao.deleteInvestigador(investigador)

    }

}

class AreaTrabajoRepositoryImple(private val areaTrabajoDao: AreasTrabajoDao) : AreasTrabajoRepository {
    override fun getAllAreasTrabajo(): Flow<List<AreaTrabajo>> = flow {
        emit(areaTrabajoDao.getAllAreasTrabajo())
    }
    override fun getAreaTrabajoById(id: Int): Flow<AreaTrabajo?> = flow {
        emit(areaTrabajoDao.getAreaTrabajoById(id))
    }
    override fun getAreaTrabajoByNombre(nombre: String): Flow<AreaTrabajo?> = flow {
        emit(areaTrabajoDao.getAreaTrabajoByNombre(nombre))
    }
    override suspend fun addAreaTrabajo(areaTrabajo: AreaTrabajo) {
        areaTrabajoDao.addAreaTrabajo(areaTrabajo)
    }
    override suspend fun updateAreaTrabajo(areaTrabajo: AreaTrabajo) {
        areaTrabajoDao.updateAreaTrabajo(areaTrabajo)
    }

    override suspend fun deleteAreaTrabajo(areaTrabajo: AreaTrabajo) {
        areaTrabajoDao.deleteAreaTrabajo(areaTrabajo)
    }




}

class LiniasTrabajoRepositoryImple(private val lineaTrabajoDao: LineasTrabajoDao) : LineasTrabajoRepository {
    override fun getAllLineasTrabajo(): Flow<List<LineaTrabajo>> = flow {
        emit(lineaTrabajoDao.getAllLineasTrabajo())
    }
    override fun getLineaTrabajoById(id: Int): Flow<LineaTrabajo?> = flow {
        emit(lineaTrabajoDao.getLineaTrabajoById(id))
    }
    override suspend fun addLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        lineaTrabajoDao.addLineaTrabajo(lineaTrabajo)
    }
    override suspend fun updateLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        lineaTrabajoDao.updateLineaTrabajo(lineaTrabajo)
    }
    override suspend fun deleteLineaTrabajo(lineaTrabajo: LineaTrabajo) {
        lineaTrabajoDao.deleteLineaTrabajo(lineaTrabajo)
    }
}

class InvestigadorLineaTrabajoRepositoryImple(private val investigadorLineaTrabajoDao: InvestigadorLineaTrabajoDao) : InvestigadorLineaTrabajoRepository{
    override fun getLineasPorInvestigador(investigadorId: Int): Flow<List<InvestigadorLineaTrabajo>> = flow {
        emit(investigadorLineaTrabajoDao.getLineasPorInvestigador(investigadorId))
    }
    override suspend fun addRelacion(relacion: InvestigadorLineaTrabajo) {
        investigadorLineaTrabajoDao.addRelacion(relacion)
    }
    override suspend fun deleteRelacion(relacion: InvestigadorLineaTrabajo) {
        investigadorLineaTrabajoDao.deleteRelacion(relacion)

    }

}

class EstudianteRepositoryImple(private val estudianteDao: EstudiantesDao) : EstudiantesRepository {
    override fun getAllEstudiantes(): Flow<List<Estudiante>> = flow {
        emit(estudianteDao.getAllEstudiantes())
    }
    override fun getAllEstudianteById(investigadorId: Int): Flow<List<Estudiante?>> = flow {
        emit(estudianteDao.getAllEstudianteById(investigadorId))
    }
    override  suspend fun addEstudiante(estudiante: Estudiante) {
        estudianteDao.addEstudiante(estudiante)
    }
    override suspend fun deleteEstudiante(estudiante: Estudiante) {
        estudianteDao.deleteEstudiante(estudiante)

    }

}

class ProyectoRepositoryImple(private val proyectoDao: ProyectosDao) : ProyectosRepository {
    override fun getAllProyectos(): Flow<List<Proyecto>> = flow {
        emit(proyectoDao.getAllProyectos())
    }
    override fun getProyectoById(id: Int): Flow<Proyecto?> = flow {
        emit(proyectoDao.getProyectoById(id))
    }
    override suspend fun addProyecto(proyecto: Proyecto) {
        proyectoDao.addProyecto(proyecto)
    }
    override suspend fun updateProyecto(proyecto: Proyecto) {
        proyectoDao.updateProyecto(proyecto)
    }
    override suspend fun deleteProyecto(proyecto: Proyecto) {
        proyectoDao.deleteProyecto(proyecto)
    }
}
class HerramientaRepositoryImple(private val herramientaDao: HerramientaDao) : HerramientaRepository{
    override fun getAllHerramientas(): Flow<List<Herramienta>> = flow {
        emit(herramientaDao.getAllHerramientas())
    }
    override fun getHerramientaById(id: Int): Flow<Herramienta?> = flow {
        emit(herramientaDao.getHerramientaById(id))
    }

    override suspend fun addHerramienta(herramienta: Herramienta) {
        herramientaDao.addHerramienta(herramienta)
    }
    override suspend fun deleteHerramienta(herramienta: Herramienta) {
        herramientaDao.deleteHerramienta(herramienta)

    }

}

class ProyectoHerramientaRepositoryImple(private val proyectoHerramientaDao: ProyectoHerramientaDao) : ProyectoHerramientaRepository{
    override fun getHerramientasPorProyecto(proyectoId: Int): Flow<List<ProyectoHerramienta>> = flow {
        emit(proyectoHerramientaDao.getHerramientasPorProyecto(proyectoId))
    }
    override suspend fun addRelacion(relacion: ProyectoHerramienta) {
        proyectoHerramientaDao.addRelacion(relacion)
    }
    override suspend fun deleteRelacion(relacion: ProyectoHerramienta) {
        proyectoHerramientaDao.deleteRelacion(relacion)
    }
}

class ProyectoInvestigadorRepositoryImple(private val proyectoInvestigadorDao: ProyectoInvestigadorDao) : ProyectoInvestigadorRepository{
    override fun getInvestigadoresPorProyecto(proyectoId: Int): Flow<List<ProyectoInvestigador?>> = flow {
        emit(proyectoInvestigadorDao.getInvestigadoresPorProyecto(proyectoId))

    }
    override fun getProyectoPorInvestigador(investigadorId: Int): Flow<List<ProyectoInvestigador?>> = flow {
        emit(proyectoInvestigadorDao.getProyectoPorInvestigador(investigadorId))
    }
    override suspend fun addRelacion(relacion: ProyectoInvestigador) {
        proyectoInvestigadorDao.addRelacion(relacion)
    }
    override suspend fun deleteRelacion(relacion: ProyectoInvestigador) {
        proyectoInvestigadorDao.deleteRelacion(relacion)

    }
}

class ArticuloRepositoryImple(private val articuloDao: ArticuloDao) : ArticuloRepository{
    override fun getAllArticulos(): Flow<List<Articulo>> = flow {
        emit(articuloDao.getAllArticulos())
    }
    override fun getArticuloById(id: Int): Flow<Articulo?> = flow {
        emit(articuloDao.getArticuloById(id))
    }
    override suspend fun addArticulo(articulo: Articulo) {
        articuloDao.addArticulo(articulo)
    }
    override suspend fun updateArticulo(articulo: Articulo) {
        articuloDao.updateArticulo(articulo)
    }
    override suspend fun deleteArticulo(articulo: Articulo) {
        articuloDao.deleteArticulo(articulo)

    }
}

class ArticuloInvestigadorRepositoryImple(private val articuloInvestigadorDao: ArticuloInvestigadorDao) : ArticuloInvestigadorRepository{
    override fun getInvestigadoresPorArticulo(articuloId: Int): Flow<List<ArticuloInvestigador>> = flow {
        emit(articuloInvestigadorDao.getInvestigadoresPorArticulo(articuloId))
    }
    override  fun getArticuloPorInvestigador(investigadorId: Int): Flow<ArticuloInvestigador?> = flow {
        emit(articuloInvestigadorDao.getArticuloPorInvestigador(investigadorId))
    }
    override suspend fun addRelacion(relacion: ArticuloInvestigador) {
        articuloInvestigadorDao.addRelacion(relacion)
    }

    override suspend fun deleteRelacion(relacion: ArticuloInvestigador) {
        articuloInvestigadorDao.deleteRelacion(relacion)
    }
}

class EventosRepositoryImple(private val eventosDao: EventosDao) : EventosRepository{
    override fun getAllEventos(): Flow<List<Evento>> = flow {
        emit(eventosDao.getAllEventos())
    }
    override fun getEventoById(id: Int): Flow<Evento?> = flow {
        emit(eventosDao.getEventoById(id))
    }
    override fun getEventoByInvestigador(investigadorId: Int): Flow<List<Evento?>> = flow {
        emit(eventosDao.getEventoByInvestigador(investigadorId))
    }
    override suspend fun addEvento(evento: Evento) {
        eventosDao.addEvento(evento)
    }
    override suspend fun updateEvento(evento: Evento) {
        eventosDao.updateEvento(evento)
        }
    override suspend fun deleteEvento(evento: Evento) {
        eventosDao.deleteEvento(evento)
    }
}