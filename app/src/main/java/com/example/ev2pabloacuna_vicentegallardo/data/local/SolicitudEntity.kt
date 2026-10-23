package com.example.ev2pabloacuna_vicentegallardo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val clienteNombre: String,
    val fecha: String,
    val evidenciaUri: String? = null
)