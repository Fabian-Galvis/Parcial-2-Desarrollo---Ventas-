package com.example.parcial2desarrollo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.parcial2desarrollo.Converters
import com.example.parcial2desarrollo.DAO.*
import com.example.parcial2desarrollo.Model.*

@Database(
    entities = [Producto::class, Cliente::class, Venta::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class) // Necesario para la conversión de tipo Date
abstract class ParcialDatabase : RoomDatabase() {

    abstract fun productosDao(): ProductosDao
    abstract fun clientesDao(): ClientesDao
    abstract fun ventasDao(): VentasDao

    companion object {
        @Volatile
        private var INSTANCE: ParcialDatabase? = null

        fun getDatabase(context: Context): ParcialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParcialDatabase::class.java,
                    "parcial_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
