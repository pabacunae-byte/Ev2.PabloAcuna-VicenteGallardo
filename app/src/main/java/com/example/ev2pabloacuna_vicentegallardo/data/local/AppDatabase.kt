package com.example.ev2pabloacuna_vicentegallardo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ev2pabloacuna_vicentegallardo.data.local.SolicitudDao
import com.example.ev2pabloacuna_vicentegallardo.data.local.SolicitudEntity

@Database(entities = [SolicitudEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solicitudDao(): SolicitudDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "solicitudes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}