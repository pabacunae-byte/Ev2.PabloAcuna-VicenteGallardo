package com.example.ev2pabloacuna_vicentegallardo.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ev2pabloacuna_vicentegallardo.viewmodel.SolicitudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudScreen(viewModel: SolicitudViewModel) {
    val estado by viewModel.estado.collectAsState()
    val contexto = LocalContext.current

    val categorias = listOf("Desarrollo web", "Soporte Técnico", "Redes", "Bases de Datos", "Sistemas")
    var expanded by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf(categorias[0]) }

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onEvidenciaAttached(it.toString())
            Toast.makeText(contexto, "Imagen seleccionada correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Formulario de Solicitud",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.titulo,
            onValueChange = { viewModel.onTituloChange(it) },
            label = { Text("Título") },
            isError = estado.errores.titulo != null,
            modifier = Modifier.fillMaxWidth()
        )
        estado.errores.titulo?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = estado.descripcion,
            onValueChange = { viewModel.onDescripcionChange(it) },
            label = { Text("Descripción") },
            isError = estado.errores.descripcion != null,
            modifier = Modifier.fillMaxWidth()
        )
        estado.errores.descripcion?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = categoriaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            categoriaSeleccionada = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { launcherGaleria.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (estado.evidenciaUri.isNullOrEmpty()) "Adjuntar Evidencia (Foto)"
                else "✓ Foto Seleccionada"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.guardarSolicitud(
                    clienteNombre = "Pablo Acuña",
                    categoria = categoriaSeleccionada
                ) { codigo ->
                    Toast.makeText(contexto, "¡Solicitud $codigo guardada!", Toast.LENGTH_LONG).show()
                    viewModel.onTituloChange("")
                    viewModel.onDescripcionChange("")
                    viewModel.onEvidenciaAttached("")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}