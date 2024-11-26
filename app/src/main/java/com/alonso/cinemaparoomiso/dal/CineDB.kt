package com.alonso.cinemaparoomiso.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alonso.cinemaparoomiso.ent.ClienteEntity
import com.alonso.cinemaparoomiso.ent.ConfiguracionEntity

@Database(entities=[ClienteEntity::class, ConfiguracionEntity::class], version = 1, exportSchema = true)
abstract class CineDB : RoomDatabase() {
    abstract fun cineDao(): CineDao
}