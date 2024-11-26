package com.alonso.cinemaparoomiso.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuraciones", primaryKeys = ["numSalas", "numAsientos"])
data class ConfiguracionEntity(
    var numSalas : Int,
    var numAsientos: Int,
    var precioPalomitas : Float
)