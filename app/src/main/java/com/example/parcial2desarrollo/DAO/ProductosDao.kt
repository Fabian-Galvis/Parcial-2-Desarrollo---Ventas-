package com.example.parcial2desarrollo.DAO

import androidx.room.*
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Model.Producto

@Dao
interface ProductosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto): Long

    @Query("SELECT * FROM productos")
    suspend fun getAllProductos(): List<Producto>

    @Query("DELETE FROM productos WHERE id = :productoId")
    suspend fun delete(productoId: Int)

    @Update
    suspend fun update(producto: Producto)

    @Query("SELECT * FROM productos WHERE id = :productoId LIMIT 1")
    suspend fun getProductoById(productoId: Int): Producto?

    @Query("UPDATE productos SET stock = :nuevoStock WHERE id = :productoId")
    suspend fun updateStock(productoId: Int, nuevoStock: Int)
}
