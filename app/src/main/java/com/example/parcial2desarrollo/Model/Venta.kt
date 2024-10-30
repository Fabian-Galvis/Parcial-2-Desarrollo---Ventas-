package com.example.parcial2desarrollo.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "ventas",
    foreignKeys = [
        ForeignKey(entity = Producto::class, parentColumns = ["id"], childColumns = ["producto_id"]),
        ForeignKey(entity = Cliente::class, parentColumns = ["id"], childColumns = ["cliente_id"])
    ],
)
class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val producto_id: Int,
    val cliente_id: Int,
    val cantidad: Int,
    val fecha: Date,
)

