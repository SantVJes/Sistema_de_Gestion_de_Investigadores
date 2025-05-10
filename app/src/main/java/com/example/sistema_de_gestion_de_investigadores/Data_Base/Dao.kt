package com.example.sistema_de_gestion_de_investigadores.Data_Base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/*
usuarioDao proporciona acceso a la tabla de usuarios en la base de datos
 */
@Dao
interface UsuariosDao {
    //verificar si existe el usuario
    @Query("SELECT * FROM Usuario WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): Usuario?
    @Insert
    suspend fun addUsuario(usuario: Usuario)
    @Update
    suspend fun updateUsuario(usuario: Usuario)
    @Delete
    suspend fun deleteUsuario(usuario: Usuario)
}


/*
investigadorDao proporciona acceso a la tabla de investigadores en la base de datos

 */
@Dao
interface InvestigadoresDao {
    @Query("SELECT * FROM Investigador")
    fun getAllInvestigadores(): Flow<List<Investigador>>

    @Query("SELECT * FROM Investigador WHERE id = :id")
    suspend fun getInvestigadorById(id: Int): Investigador?

    @Insert
    suspend fun addInvestigador(investigador: Investigador)

    @Update
    suspend fun updateInvestigador(investigador: Investigador)

    @Delete
    suspend fun deleteInvestigador(investigador: Investigador)


}

/*
areaTrabajoDao proporciona acceso a la tabla de areas de trabajo en la base de datos
 */
@Dao
interface AreasTrabajoDao{
    @Query("SELECT * FROM AreaTrabajo")
    suspend fun getAllAreasTrabajo(): List<AreaTrabajo>
    @Query("SELECT * FROM AreaTrabajo WHERE id = :id")
    suspend fun getAreaTrabajoById(id: Int): AreaTrabajo?
    @Query("SELECT * FROM AreaTrabajo WHERE nombre = :nombre")
    suspend fun getAreaTrabajoByNombre(nombre: String): AreaTrabajo?
    @Insert
    suspend fun addAreaTrabajo(areaTrabajo: AreaTrabajo)
    @Update
    suspend fun updateAreaTrabajo(areaTrabajo: AreaTrabajo)
    @Delete
    suspend fun deleteAreaTrabajo(areaTrabajo: AreaTrabajo)
}

/*
lineaTrabajoDao proporciona acceso a la tabla de lineas de trabajo en la base de datos
 */
@Dao
interface LineasTrabajoDao{
    @Query("SELECT * FROM LineaTrabajo")
    suspend fun getAllLineasTrabajo(): List<LineaTrabajo>
    @Query("SELECT * FROM LineaTrabajo WHERE id = :id")
    suspend fun getLineaTrabajoById(id: Int): LineaTrabajo?
    @Insert
    suspend fun addLineaTrabajo(lineaTrabajo: LineaTrabajo)
    @Update
    suspend fun updateLineaTrabajo(lineaTrabajo: LineaTrabajo)
    @Delete
    suspend fun deleteLineaTrabajo(lineaTrabajo: LineaTrabajo)
}

/*
investigadorLineaTrabajoDao proporciona acceso a la tabla intermedia de investigadores y lineas de trabajo en la base de datos

 */
@Dao
interface InvestigadorLineaTrabajoDao{
    @Query("SELECT * FROM InvestigadorLineaTrabajo WHERE investigadorId = :investigadorId")
    suspend fun getLineasPorInvestigador(investigadorId: Int): List<InvestigadorLineaTrabajo>

    @Insert
    suspend fun addRelacion(relacion: InvestigadorLineaTrabajo)

    @Delete
    suspend fun deleteRelacion(relacion: InvestigadorLineaTrabajo)
}
/*
estudianteDao proporciona acceso a la tabla de estudiantes en la base de datos

 */
@Dao
interface EstudiantesDao{
    @Query("SELECT * FROM Estudiante")
    suspend fun getAllEstudiantes(): List<Estudiante>

    @Query("SELECT * FROM Estudiante WHERE investigadorId = :investigadorId")
    suspend fun getAllEstudianteById(investigadorId: Int): List<Estudiante?>

    @Insert
    suspend fun addEstudiante(estudiante: Estudiante)

    @Delete
    suspend fun deleteEstudiante(estudiante: Estudiante)

}

/*
proyectoDao proporciona acceso a la tabla de proyectos en la base de datos
 */
@Dao
interface ProyectosDao{
    @Query("SELECT * FROM Proyecto")
     fun getAllProyectos(): Flow<List<Proyecto>>

    @Query("SELECT * FROM Proyecto WHERE id = :id")
    suspend fun getProyectoById(id: Int): Proyecto?
    @Insert
    suspend fun addProyecto(proyecto: Proyecto)
    @Update
    suspend fun updateProyecto(proyecto: Proyecto)
    @Delete
    suspend fun deleteProyecto(proyecto: Proyecto)

}
/*
herramientaDao proporciona acceso a la tabla de herramientas en la base de datos
 */
@Dao
interface HerramientaDao{
    @Query("SELECT * FROM Herramienta")
    fun getAllHerramientas(): Flow<List<Herramienta>>
    @Query("SELECT * FROM Herramienta WHERE id = :id")
    suspend fun getHerramientaById(id: Int): Herramienta?
    @Insert
    suspend fun addHerramienta(herramienta: Herramienta)
    @Delete
    suspend fun deleteHerramienta(herramienta: Herramienta)
}

/*
proyectoHerramientaDao proporciona acceso a la tabla intermedia de proyectos y herramientas en la base de datos
 */
@Dao
interface ProyectoHerramientaDao{
    @Query("SELECT * FROM ProyectoHerramienta WHERE proyectoId = :proyectoId")
    suspend fun getHerramientasPorProyecto(proyectoId: Int): List<ProyectoHerramienta>
    @Insert
    suspend fun addRelacion(relacion: ProyectoHerramienta)
    @Delete
    suspend fun deleteRelacion(relacion: ProyectoHerramienta)
}
/*
proyectoInvestigadorDao proporciona acceso a la tabla intermedia de proyectos y investigadores en la base de datos

 */
@Dao
interface ProyectoInvestigadorDao{
    @Query("SELECT * FROM ProyectoInvestigador WHERE proyectoId = :proyectoId")
    suspend fun getInvestigadoresPorProyecto(proyectoId: Int): List<ProyectoInvestigador?>
    @Query("SELECT * FROM ProyectoInvestigador WHERE investigadorId = :investigadorId")
    suspend fun getProyectoPorInvestigador(investigadorId: Int): List<ProyectoInvestigador?>
    @Insert
    suspend fun addRelacion(relacion: ProyectoInvestigador)
    @Delete
    suspend fun deleteRelacion(relacion: ProyectoInvestigador)

}

/*
articuloDao proporciona acceso a la tabla de articulos en la base de datos
 */
@Dao
interface ArticuloDao{
    @Query("SELECT * FROM Articulo")
    suspend fun getAllArticulos(): List<Articulo>
    @Query("SELECT * FROM Articulo WHERE id = :id")
    suspend fun getArticuloById(id: Int): Articulo?

    @Insert
    suspend fun addArticulo(articulo:Articulo)

    @Update
    suspend fun updateArticulo(articulo:Articulo)

    @Delete
    suspend fun deleteArticulo(articulo:Articulo)


}
/*
articuloInvestigadorDao proporciona acceso a la tabla intermedia de articulos y investigadores en la base de datos

 */
@Dao
interface  ArticuloInvestigadorDao{
    @Query("SELECT * FROM ArticuloInvestigador WHERE articuloId = :articuloId")
    suspend fun getInvestigadoresPorArticulo(articuloId: Int): List<ArticuloInvestigador>

    @Query("SELECT * FROM ArticuloInvestigador WHERE investigadorId = :investigadorId")
    suspend fun getArticuloPorInvestigador(investigadorId: Int): List<ArticuloInvestigador>

    @Insert
    suspend fun addRelacion(relacion: ArticuloInvestigador)

    @Delete
    suspend fun deleteRelacion(relacion: ArticuloInvestigador)
}

/*
eventoDao proporciona acceso a la tabla de eventos en la base de datos

 */

@Dao
interface EventosDao{
    @Query("SELECT * FROM Evento")
    suspend fun getAllEventos(): List<Evento>
    @Query("SELECT * FROM Evento WHERE id = :id")
    suspend fun getEventoById(id: Int): Evento?
    @Query("SELECT * FROM Evento WHERE investigadorId = :investigadorId")
    suspend fun getEventoByInvestigador(investigadorId: Int): List<Evento?>

    @Insert
    suspend fun addEvento(evento: Evento)

    @Update
    suspend fun updateEvento(evento: Evento)

    @Delete
    suspend fun deleteEvento(evento: Evento)


}