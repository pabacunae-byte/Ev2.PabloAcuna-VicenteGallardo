package com.example.ev2pabloacuna_vicentegallardo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ev2pabloacuna_vicentegallardo.data.local.AppDatabase
import com.example.ev2pabloacuna_vicentegallardo.ui.screen.SolicitudScreen
import com.example.ev2pabloacuna_vicentegallardo.viewmodel.SolicitudViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SolicitudViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getDatabase(applicationContext)
                @Suppress("UNCHECKED_CAST")
                return SolicitudViewModel(db.solicitudDao()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SolicitudScreen(viewModel = viewModel)
        }
    }
}