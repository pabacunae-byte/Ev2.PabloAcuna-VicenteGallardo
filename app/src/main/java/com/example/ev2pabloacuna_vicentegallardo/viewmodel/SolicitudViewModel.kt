package com.example.ev2pabloacuna_vicentegallardo.viewmodel

import com.example.ev2pabloacuna_vicentegallardo.data.local.SolicitudEntity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2pabloacuna_vicentegallardo.data.local.SolicitudDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class ErroresFormulario(
    val titulo: String? = null,
    val descripcion: String? = null
)

data class SolicitudFormState(
    val titulo: String = "",
    val descripcion: String = "",
    val evidenciaUri: String? = null,
    val errores: ErroresFormulario = ErroresFormulario()
)

class SolicitudViewModel(private val dao: SolicitudDao) : ViewModel() {

    private val _estado = MutableStateFlow(SolicitudFormState())
    val estado: StateFlow<SolicitudFormState> = _estado.asStateFlow()

    fun onTituloChange(nuevoTitulo: String) {
        _estado.update {
            it.copy(
                titulo = nuevoTitulo,
                errores = it.errores.copy(titulo = null)
            )
        }
    }

    fun onDescripcionChange(nuevaDescripcion: String) {
        _estado.update {
            it.copy(
                descripcion = nuevaDescripcion,
                errores = it.errores.copy(descripcion = null)
            )
        }
    }

    fun onEvidenciaAttached(uri: String?) {
        _estado.update { it.copy(evidenciaUri = uri) }
    }

    fun guardarSolicitud(
        clienteNombre: String,
        categoria: String = "Desarrollo web",
        onExito: (String) -> Unit
    ) {
        val t = _estado.value.titulo.trim()
        val d = _estado.value.descripcion.trim()

        val errTitulo = if (t.isEmpty()) "El título es obligatorio" else null
        val errDesc = if (d.length < 10) "Mínimo 10 caracteres" else null

        if (errTitulo != null || errDesc != null) {
            _estado.update { it.copy(errores = ErroresFormulario(errTitulo, errDesc)) }
            return
        }

        val nuevoCodigo = "REQ-${Random.nextInt(10000, 99999)}"
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        val nuevaSolicitud = SolicitudEntity(
            codigo = nuevoCodigo,
            clienteNombre = clienteNombre,
            titulo = t,
            descripcion = d,
            categoria = categoria,
            evidenciaUri = _estado.value.evidenciaUri,
            fecha = fechaActual
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertar(nuevaSolicitud)
            launch(Dispatchers.Main) {
                onExito(nuevoCodigo)
            }
        }
    }
}