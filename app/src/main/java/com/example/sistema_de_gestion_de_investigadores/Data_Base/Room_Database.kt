package com.example.sistema_de_gestion_de_investigadores.Data_Base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Usuario::class,Investigador::class,AreaTrabajo::class,LineaTrabajo::class,InvestigadorLineaTrabajo::class,Estudiante::class,Proyecto::class,Herramienta::class,ProyectoHerramienta::class,ProyectoInvestigador::class,Articulo::class,ArticuloInvestigador::class,Evento::class], version = 1, exportSchema = false)
abstract class InvestigadoresDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuariosDao
    abstract fun investigadoresDao(): InvestigadoresDao
    abstract fun areasTrabajoDao(): AreasTrabajoDao
    abstract fun lineasTrabajoDao(): LineasTrabajoDao
    abstract fun investigadorLineaTrabajoDao(): InvestigadorLineaTrabajoDao
    abstract fun estudiantesDao(): EstudiantesDao
    abstract fun proyectosDao(): ProyectosDao
    abstract fun herramientasDao(): HerramientaDao
    abstract fun proyectoHerramientaDao(): ProyectoHerramientaDao
    abstract fun proyectoInvestigadorDao(): ProyectoInvestigadorDao
    abstract fun articulosDao(): ArticuloDao
    abstract fun articuloInvestigadorDao(): ArticuloInvestigadorDao
    abstract fun eventosDao(): EventosDao

    companion object{
        @Volatile
        private var instance: InvestigadoresDatabase? = null
        fun getDatabase(context: Context): InvestigadoresDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    InvestigadoresDatabase::class.java,
                    "investigadores_database"
                )
                    .build()
                    .also { instance = it }
            }
        }
    }


}



