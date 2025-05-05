package com.example.sistema_de_gestion_de_investigadores.Data_Base

import android.content.Context

/*
 *class AppContainer (context: Context){
    private  val DirrecionDatabase = DirreccionDatabase.getDatabase(context)
    private  val DirrecionDao = DirrecionDatabase.direccionDao()
    private  val DirrecionRepositoy = DireccionRepositoryImpl(DirrecionDao)

    fun provideDirrecionRepositoy(): DireccionRepository {
        return  DirrecionRepositoy
    }

}*/
class App_Container (context: Context){
    private val getDatabase = InvestigadoresDatabase.getDatabase(context)
    private val getUsuariosDao = getDatabase.UsuarioDao()
    private val getInvestigadoresDao = getDatabase.investigadoresDao()
    private val getAreasTrabajoDao = getDatabase.areasTrabajoDao()
    private val getLineasTrabajoDao = getDatabase.lineasTrabajoDao()
    private val getInvestigadorLineaTrabajoDao = getDatabase.investigadorLineaTrabajoDao()
    private val getEstudiantesDao = getDatabase.estudiantesDao()
    private val getProyectosDao = getDatabase.proyectosDao()
    private val getHerramientasDao = getDatabase.herramientasDao()
    private val getProyectoHerramientaDao = getDatabase.proyectoHerramientaDao()
    private val getProyectoInvestigadorDao = getDatabase.proyectoInvestigadorDao()
    private val getArticulosDao = getDatabase.articulosDao()
    private val getArticuloInvestigadorDao = getDatabase.articuloInvestigadorDao()
    private val getEventosDao = getDatabase.eventosDao()
    private val getUsuarioRepository = UsuarioRepositoryImple(getUsuariosDao)
    private val getInvestigadorRepository = InvestigadorRepositoryImple(getInvestigadoresDao)
    private val getAreaTrabajoRepository = AreaTrabajoRepositoryImple(getAreasTrabajoDao)
    private val getLineaTrabajoRepository = LiniasTrabajoRepositoryImple(getLineasTrabajoDao)
    private val getInvestigadorLineaTrabajoRepository = InvestigadorLineaTrabajoRepositoryImple(getInvestigadorLineaTrabajoDao)
    private val getEstudianteRepository = EstudianteRepositoryImple(getEstudiantesDao)
    private val getProyectoRepository = ProyectoRepositoryImple(getProyectosDao)
    private val getHerramientaRepository = HerramientaRepositoryImple(getHerramientasDao)
    private val getProyectoHerramientaRepository = ProyectoHerramientaRepositoryImple(getProyectoHerramientaDao)
    private val getProyectoInvestigadorRepository = ProyectoInvestigadorRepositoryImple(getProyectoInvestigadorDao)
    private val getArticuloRepository = ArticuloRepositoryImple(getArticulosDao)
    private val getArticuloInvestigadorRepository = ArticuloInvestigadorRepositoryImple(getArticuloInvestigadorDao)
    private val getEventoRepository = EventosRepositoryImple(getEventosDao)

    fun provideUsuarioRepository(): UsuariosRepository {
        return getUsuarioRepository
    }
    fun provideInvestigadorRepository(): InvestigadoresRepository {
        return getInvestigadorRepository
    }
    fun provideAreaTrabajoRepository(): AreasTrabajoRepository {
        return getAreaTrabajoRepository
    }
    fun provideLineaTrabajoRepository(): LineasTrabajoRepository {
        return getLineaTrabajoRepository
    }
    fun provideInvestigadorLineaTrabajoRepository(): InvestigadorLineaTrabajoRepository {
        return getInvestigadorLineaTrabajoRepository
    }
    fun provideEstudianteRepository(): EstudiantesRepository {
        return getEstudianteRepository
    }
    fun provideProyectoRepository(): ProyectosRepository {
        return getProyectoRepository
    }
    fun provideHerramientaRepository(): HerramientaRepository {
        return getHerramientaRepository
    }
    fun provideProyectoHerramientaRepository(): ProyectoHerramientaRepository {
        return getProyectoHerramientaRepository
    }
    fun provideProyectoInvestigadorRepository(): ProyectoInvestigadorRepository {
        return getProyectoInvestigadorRepository
    }
    fun provideArticuloRepository(): ArticuloRepository {
        return getArticuloRepository
    }
    fun provideArticuloInvestigadorRepository(): ArticuloInvestigadorRepository {
        return getArticuloInvestigadorRepository
    }
    fun provideEventoRepository(): EventosRepository {
        return getEventoRepository
    }


}