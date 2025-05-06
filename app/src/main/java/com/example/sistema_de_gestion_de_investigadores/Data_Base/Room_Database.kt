package com.example.sistema_de_gestion_de_investigadores.Data_Base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
                    .addCallback(DatabaseCallback())
                    .build()
                    .also { instance = it }
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                //Usuario
                val daoUser = instance?.UsuarioDao()
                daoUser?.addUsuario(
                    Usuario(
                        username = "admin",
                        password = "admin"
                    )
                )
                //Areas de trabajo
                val daoArea = instance?.areasTrabajoDao()
                val area1 = daoArea?.addAreaTrabajo(
                    AreaTrabajo(
                        id = 1,
                        nombre = "Ingeniería de Software",
                        jefeArea = "Juan Pérez",
                        lugar = "Ciudad Mazatlan"
                    )
                )
                val area2 = daoArea?.addAreaTrabajo(
                    AreaTrabajo(
                        id = 2,
                        nombre = "Inteligencia Artificial",
                        jefeArea = "Luis Anguel ",
                        lugar = "Unidad 1 "
                    )
                )
                val area3 = daoArea?.addAreaTrabajo(
                    AreaTrabajo(
                        id = 3,
                        nombre = "Robotica",
                        jefeArea = "Jesus Mejorada",
                        lugar = "Ciudad Guadalajara"
                    )
                )
                //Lineas de trabajo
                val daoLinea = instance?.lineasTrabajoDao()
                val linia1 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 1,
                        nombre = "Desarrollo de Software"
                    )
                )
                val linia2 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 2,
                        nombre = "Robotica"
                    )
                )
                val linia3 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 3,
                        nombre = "IA"
                    )
                )
                val linia4 = daoLinea?.addLineaTrabajo(
                 LineaTrabajo(
                     id = 4,
                       nombre = "Electronica"

                        )
                 )
                val linia5 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 5,
                        nombre = "Alimentos"
                    )
                )
                val linia6 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 6,
                        nombre = "Energias Renovables"
                    )
                )
                val linia7 = daoLinea?.addLineaTrabajo(
                    LineaTrabajo(
                        id = 7,
                        nombre = "Medicina"
                    )
                )
                //Herramientas
                val daoHerramienta = instance?.herramientasDao()
                val herramientas = listOf(
                    Herramienta(id = 1, nombre = "TensorFlow", tipo = "Software"),
                    Herramienta(id = 2, nombre = "Arduino Uno", tipo = "Hardware"),
                    Herramienta(id = 3, nombre = "MATLAB", tipo = "Software"),
                    Herramienta(id = 4, nombre = "Raspberry Pi", tipo = "Hardware"),
                    Herramienta(id = 5, nombre = "Jupyter Notebook", tipo = "Software")
                )
                herramientas.forEach { herramienta ->
                    daoHerramienta?.addHerramienta(herramienta)
                }



                //Investigadores
                val daoInvestigador = instance?.investigadoresDao()
                val investigadores = listOf(
                    Investigador(
                        id = 1,
                        nombre = "Juan López",
                        especialidad = "Visión por Computador",
                        carrera = "Ingeniería en Computación",
                        nivelSNII = "Nivel I",
                        fechaSNII = "2023-04-10",
                        areaId = 1
                    ),
                    Investigador(
                        id = 2,
                        nombre = "María Rodríguez",
                        especialidad = "Robótica Móvil",
                        carrera = "Ingeniería Mecatrónica",
                        nivelSNII = "Nivel II",
                        fechaSNII = "2021-08-15",
                        areaId = 2
                    ),
                    Investigador(
                        id = 3,
                        nombre = "Carlos Sánchez",
                        especialidad = "Sistemas Embebidos",
                        carrera = "Ingeniería Electrónica",
                        nivelSNII = "Candidato",
                        fechaSNII = "2022-01-20",
                        areaId = 1
                    ),
                    Investigador(
                        id = 4,
                        nombre = "Laura Fernández",
                        especialidad = "Procesamiento de Lenguaje Natural",
                        carrera = "Ciencias de la Computación",
                        nivelSNII = "Nivel III",
                        fechaSNII = "2019-11-30",
                        areaId = 1
                    ),
                    Investigador(
                        id = 5,
                        nombre = "Andrés Morales",
                        especialidad = "Fuentes de Energía Renovable",
                        carrera = "Ingeniería en Energía",
                        nivelSNII = "Nivel I",
                        fechaSNII = "2020-05-12",
                        areaId = 3
                    )
                )
                investigadores.forEach { investigador ->
                    daoInvestigador?.addInvestigador(investigador)
                }


                // Relación investigador-línea de trabajo
                val daoInvestigadorLinea = instance?.investigadorLineaTrabajoDao()
                val relaciones = listOf(
                    InvestigadorLineaTrabajo(investigadorId = 1, lineaTrabajoId = 1),  // Juan López con Desarrollo de Software
                    InvestigadorLineaTrabajo(investigadorId = 2, lineaTrabajoId = 2),  // María Rodríguez con Robótica
                    InvestigadorLineaTrabajo(investigadorId = 3, lineaTrabajoId = 4),  // Carlos Sánchez con Electrónica
                    InvestigadorLineaTrabajo(investigadorId = 4, lineaTrabajoId = 3),  // Laura Fernández con IA
                    InvestigadorLineaTrabajo(investigadorId = 5, lineaTrabajoId = 6)   // Andrés Morales con Energías Renovables
                )
                relaciones.forEach { relacion ->
                    daoInvestigadorLinea?.addRelacion(relacion)
                }

                //Estudiantes
                val daoEstudiantes = instance?.estudiantesDao()
                val estudiantes = listOf(
                    Estudiante(
                        id = 1,
                        nombre = "Juan Pérez",
                        tipo = "Practicante",
                        carrera = "Ingeniería en Software",
                        escuela = "Universidad XYZ",
                        fechaInicio = "2022-01-01",
                        fechaFin = "2022-12-31",
                        investigadorId = 1
                    ),
                    Estudiante(
                        id = 2,
                        nombre = "María Rodríguez",
                        tipo = "Serv. Social",
                        carrera = "Licenciatura en Administración",
                        escuela = "Universidad ABC",
                        fechaInicio = "2021-06-01",
                        fechaFin = "2022-05-31",
                        investigadorId = 2
                    ),
                    Estudiante(
                        id = 3,
                        nombre = "Carlos Sánchez",
                        tipo = "Maestría",
                        carrera = "Ciencias de la Computación",
                        escuela = "Universidad XYZ",
                        fechaInicio = "2020-09-01",
                        fechaFin = "2021-08-31",
                        investigadorId = 3
                    ),
                    Estudiante(
                        id = 4,
                        nombre = "Laura Fernández",
                        tipo = "Doctorado",
                        carrera = "Ingeniería Industrial",
                        escuela = "Universidad DEF",
                        fechaInicio = "2020-07-01",
                        fechaFin = "2021-06-30",
                        investigadorId = 4
                    ),
                    Estudiante(
                        id = 5,
                        nombre = "Andrés Morales",
                        tipo = "Practicante",
                        carrera = "Ingeniería Mecatrónica",
                        escuela = "Universidad XYZ",
                        fechaInicio = "2022-02-01",
                        fechaFin = "2022-11-30",
                        investigadorId = 4
                    )
                )
                estudiantes.forEach { estudiantesre ->
                    daoEstudiantes?.addEstudiante(estudiantesre)
                }

                //Proyectos
                val daoProyectos = instance?.proyectosDao()
                val proyectos = listOf(
                    Proyecto(
                        id = 1,
                        nombre = "Desarrollo de Aplicaciones Móviles",
                        fechaInicio = "2022-01-01",
                        fechaFin = "2022-12-31"
                    ),
                    Proyecto(
                        id = 2,
                        nombre = "Investigación de Robótica",
                        fechaInicio = "2021-06-01",
                        fechaFin = "2022-05-31"
                    ),
                    Proyecto(
                        id = 3,
                        nombre = "Diseño de Sistemas Embebidos",
                        fechaInicio = "2020-09-01",
                        fechaFin = "2021-08-31"
                    ),
                    Proyecto(
                        id = 4,
                        nombre = "Desarrollo de Plataformas Web",
                        fechaInicio = "2020-07-01",
                        fechaFin = "2021-06-30"
                    )
                )
                proyectos.forEach { proyecto ->
                    daoProyectos?.addProyecto(proyecto)
                }
                //Relaciones Investigador-Proyecto
                val daoInvestigadorProyecto = instance?.proyectoInvestigadorDao()

                val relacionesProyecto = listOf(
                    ProyectoInvestigador(proyectoId = 1, investigadorId = 1, esPrincipal = true),
                    ProyectoInvestigador(proyectoId = 2, investigadorId = 2, esPrincipal = true),
                    ProyectoInvestigador(proyectoId = 3, investigadorId = 3, esPrincipal = true),
                    ProyectoInvestigador(proyectoId = 4, investigadorId = 4, esPrincipal = true),
                    ProyectoInvestigador(proyectoId = 1, investigadorId = 2, esPrincipal = false),
                    ProyectoInvestigador(proyectoId = 2, investigadorId = 3, esPrincipal = false),
                    ProyectoInvestigador(proyectoId = 3, investigadorId = 4, esPrincipal = false),
                    ProyectoInvestigador(proyectoId = 4, investigadorId = 5, esPrincipal = false)
                )
                relacionesProyecto.forEach { relacion ->
                    daoInvestigadorProyecto?.addRelacion(relacion)
                }

                //Relaciones Proyecto-Herramienta
                val daoProyectoHerramienta = instance?.proyectoHerramientaDao()
                val relacionesHerramienta = listOf(
                    ProyectoHerramienta(proyectoId = 1 , herramientaId = 2),
                    ProyectoHerramienta(proyectoId = 2 , herramientaId = 2),
                    ProyectoHerramienta(proyectoId = 3 , herramientaId = 4),
                    ProyectoHerramienta(proyectoId = 4 , herramientaId = 4),
                    ProyectoHerramienta(proyectoId = 1 , herramientaId = 3),
                    ProyectoHerramienta(proyectoId = 2 , herramientaId = 3),
                    ProyectoHerramienta(proyectoId = 3 , herramientaId = 1),
                    ProyectoHerramienta(proyectoId = 4 , herramientaId = 1)
                )
                relacionesHerramienta.forEach { relacion ->
                    daoProyectoHerramienta?.addRelacion(relacion)
                }

                //Articulos
                val daoArticulos = instance?.articulosDao()
                val articulos = listOf(
                    Articulo(
                        id = 1,
                        nombre = "Desarrollo de Aplicaciones Móviles",
                        revista = "Revista de Software",
                        pais = "México",
                        anio = 2022,
                        fechaPublicacion = "2022-01-01",
                        doi = "10.1234/abcd1234",
                        url = "https://example.com/articulo1"
                    ),
                    Articulo(
                        id = 2,
                        nombre = "Investigación de Robótica",
                        revista = "Revista de Robótica",
                        pais = "México",
                        anio = 2021,
                        fechaPublicacion = "2021-06-01",
                        doi = "10.1234/efgh5678",
                        url = "https://example.com/articulo2"
                    ),
                    Articulo(
                        id = 3,
                        nombre = "Diseño de Sistemas Embebidos",
                        revista = "Revista de Sistemas",
                        pais = "México",
                        anio = 2020,
                        fechaPublicacion = "2020-09-01",
                        doi = "10.1234/ijkl9012",
                        url = "https://example.com/articulo3"
                    ),
                    Articulo(
                        id = 4,
                        nombre = "Desarrollo de Plataformas Web",
                        revista = "Revista de Software",
                        pais = "México",
                        anio = 20,
                        fechaPublicacion = "2020-07-01",
                        doi = "10.1234/mnop3456",
                        url = "https://example.com/articulo4"
                    )
                    )

                articulos.forEach { articulo ->
                    daoArticulos?.addArticulo(articulo)
                }

                //Relaciones Articulo-Investigador
                val daoArticuloInvestigador = instance?.articuloInvestigadorDao()
                val relacionesArticulo = listOf(
                    ArticuloInvestigador(articuloId = 1, investigadorId = 1, esPrincipal = true),
                    ArticuloInvestigador(articuloId = 2, investigadorId = 2, esPrincipal = true),
                    ArticuloInvestigador(articuloId = 3, investigadorId = 3, esPrincipal = true),
                    ArticuloInvestigador(articuloId = 4, investigadorId = 4, esPrincipal = true),
                    ArticuloInvestigador(articuloId = 1, investigadorId = 2, esPrincipal = false),
                    ArticuloInvestigador(articuloId = 2, investigadorId = 3, esPrincipal = false),
                    ArticuloInvestigador(articuloId = 3, investigadorId = 4, esPrincipal = false),
                    ArticuloInvestigador(articuloId = 4, investigadorId = 5, esPrincipal = false)
                    )
                relacionesArticulo.forEach { relacion ->
                    daoArticuloInvestigador?.addRelacion(relacion)
                }

                //eventos
                val daoEventos = instance?.eventosDao()

                val eventos = listOf(
                    Evento(
                        id = 1,
                        nombre = "Seminario de Robótica",
                        lugar = "Aula 101",
                        fecha = "2023-05-15",
                        duracion = "2 horas",
                        empresaInvita = "XYZ Inc.",
                        investigadorId = 1
                    ),
                    Evento(
                        id = 2,
                        nombre = "Conferencia de Inteligencia Artificial",
                        lugar = "Sala de Conferencias",
                        fecha = "2023-06-21"
                        ,
                        duracion = "4 horas",
                        empresaInvita = "ABC Corp.",
                        investigadorId = 2
                    ),
                    Evento(
                        id = 3,
                        nombre = "Taller de Programación",
                        lugar = "Biblioteca Central",
                        fecha = "2023-0-8-21",
                        duracion = "3 horas",
                        empresaInvita = "DEF Inc.",
                        investigadorId = 3
                    ),
                    Evento(
                        id = 4,
                        nombre = "Conferencia de Seguridad Informática",
                        lugar = "Oficina de Seguridad"
                        ,
                        fecha = "2023-07-10",
                        duracion = "2 horas",
                        empresaInvita = "GHI Corp.",
                        investigadorId = 4
                    )
                )
                eventos.forEach { evento ->
                    daoEventos?.addEvento(evento)
                }

            }
        }
    }


}



