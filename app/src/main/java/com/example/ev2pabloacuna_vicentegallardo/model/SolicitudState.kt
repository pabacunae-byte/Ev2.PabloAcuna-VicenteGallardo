package com.example.ev2pabloacuna_vicentegallardo.model

// Contiene los mensajes de error individuales por cada campo
data class SolicitudErrores(
    val titulo: String? = null,
    val descripcion: String? = null
)

// Representa el estado inmutable de la pantalla de formulario
data class SolicitudUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "Desarrollo web",
    val evidenciaUri: String? = null,
    val errores: SolicitudErrores = SolicitudErrores()
)
