package com.example.parcial2desarrollo.DAO

import androidx.room.*
import com.example.parcial2desarrollo.Model.Producto
import com.example.parcial2desarrollo.Model.Venta

@Dao
interface VentasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venta: Venta): Long

    @Query("SELECT * FROM ventas")
    suspend fun getAllVentas(): List<Venta>

    @Query("DELETE FROM ventas WHERE id = :ventaId")
    suspend fun delete(ventaId: Int)

    @Update
    suspend fun update(venta: Venta)

    @Transaction
    @Query("SELECT * FROM ventas WHERE cliente_id = :clienteId AND producto_id = :productoId")
    suspend fun getVentasConClientesYProductos(clienteId: Int, productoId: Int): List<Venta>

    @Query("SELECT * FROM ventas WHERE id = :ventaId LIMIT 1")
    suspend fun getVentaById(ventaId: Int): Venta?

    @Query("""
        SELECT v.*, p.nombre AS productoNombre, c.nombre AS clienteNombre 
        FROM ventas v
        JOIN productos p ON v.producto_id = p.id
        JOIN clientes c ON v.cliente_id = c.id
    """)
    suspend fun getAllVentasConNombres(): List<Venta>
}
