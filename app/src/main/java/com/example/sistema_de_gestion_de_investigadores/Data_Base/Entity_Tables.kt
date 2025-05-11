package com.example.sistema_de_gestion_de_investigadores.Data_Base

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey



//Tabla Usuarios
@Entity
data class Usuario(
    @PrimaryKey val username: String,
    val password: String,
)


//Tabla Investigadores
@Entity
data class Investigador(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val especialidad: String,
    val carrera: String,
    val nivelSNII: String?, // "Candidato", "Nivel I", "Nivel II", "Nivel III"
    val fechaSNII: String?, // formato ISO yyyy-MM-dd
    val areaId: Int
)

//Tablas Areas de Trabajo
@Entity
data class AreaTrabajo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var jefeArea: String,
    var lugar: String, //si es ciudad o unidad
)

//Tabla Linias de trabajo
@Entity
data class LineaTrabajo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String // Robótica, IA, Electrónica, etc.
)

//Tabla de relación entre investigadores y líneas de trabajo (many-to-many)
@Entity(primaryKeys = ["investigadorId", "lineaTrabajoId"])
data class InvestigadorLineaTrabajo(
    val investigadorId: Int,
    val lineaTrabajoId: Int
)

//tabla estudiantes de los investigadores
@Entity
data class Estudiante(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val tipo: String, // Practicante, Serv. Social, Maestría, Doctorado
    val carrera: String,
    val escuela: String,
    val fechaInicio: String,
    val fechaFin: String,
    val investigadorId: Int
)

//Tabla de proyectos
@Entity
data class Proyecto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val fechaInicio: String,
    val fechaFin: String
)

//tabla de Herramientas de investigacion
@Entity
data class Herramienta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val tipo: String // Catalogado (software, hardware, etc.)
)

//Tabla de relación entre proyectos y herramientas (many-to-many)
@Entity(primaryKeys = ["proyectoId", "herramientaId"])
data class ProyectoHerramienta(
    val proyectoId: Int,
    val herramientaId: Int
)

//Tabla de relación entre investigadores y proyectos (many-to-many)
@Entity(primaryKeys = ["proyectoId", "investigadorId"])
data class ProyectoInvestigador(
    val proyectoId: Int,
    val investigadorId: Int,
    val esPrincipal : Boolean // true si es el primer investigador registrado
)

//Tabla articulos publicados
@Entity
data class Articulo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var revista: String,
    var pais: String,
    var anio: Int,
    var fechaPublicacion: String,
    var doi: String,
    var url: String
)

//Tabla de relación entre investigadores y artículos (many-to-many)
@Entity(primaryKeys = ["articuloId", "investigadorId"])
data class ArticuloInvestigador(
    val articuloId: Int,
    val investigadorId: Int,
    val esPrincipal: Boolean
)

//tabla eventos
@Entity
data class Evento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val lugar: String,
    val fecha: String,
    val duracion: String,
    val empresaInvita: String,
    val investigadorId: Int
)
