package com.example.ev2pabloacuna_vicentegallardo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(solicitud: SolicitudEntity)

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<SolicitudEntity>>
}