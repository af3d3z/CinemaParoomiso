package com.alonso.cinemaparoomiso.dal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alonso.cinemaparoomiso.ent.ClienteEntity
import com.alonso.cinemaparoomiso.ent.ConfiguracionEntity

@Dao
interface CineDao {
    @Query("SELECT * FROM clientes")
    suspend fun getAllClients(): List<ClienteEntity>
    @Query("SELECT * FROM clientes WHERE id == :id")
    suspend fun getClient(id: Int) : ClienteEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(clienteEntity: ClienteEntity)
    @Update
    suspend fun updateClient(clienteEntity: ClienteEntity)
    @Delete
    suspend fun deleteClient(clienteEntity: ClienteEntity)
    @Query("SELECT * FROM configuraciones")
    suspend fun getAllConfigs(): List<ConfiguracionEntity>
    /*@Query("SELECT * FROM configuraciones WHERE numSalas = :numSalas and numAsientos = :numAsientos")
    suspend fun getConfig(numSalas: Int, numAsientos: Int)*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(configuracionEntity: ConfiguracionEntity)
    @Update
    suspend fun updateConfig(configuracionEntity: ConfiguracionEntity)
    @Delete
    suspend fun deleteConfig(configuracionEntity: ConfiguracionEntity)
}