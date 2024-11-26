package com.alonso.cinemaparoomiso.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClienteEntity (
    @PrimaryKey
    var id : Long,
    var salaElegida : Int = 0,
    var palomitas: Int
)