package com.example.parcial2desarrollo.Repository

import com.example.parcial2desarrollo.DAO.VentasDao
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Model.Venta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VentasRepository(private val ventasDao: VentasDao) {
    suspend fun insert(venta: Venta): Long = withContext(Dispatchers.IO) {
        ventasDao.insert(venta)
    }

    suspend fun getAllVentas(): List<Venta> = withContext(Dispatchers.IO) {
        ventasDao.getAllVentas()
    }

    suspend fun delete(ventaId: Int) = withContext(Dispatchers.IO) {
        ventasDao.delete(ventaId)
    }

    suspend fun update(venta: Venta) = withContext(Dispatchers.IO) {
        ventasDao.update(venta)
    }

    suspend fun getVentasConClientesYProductos(clienteId: Int, productoId: Int): List<Venta> = withContext(Dispatchers.IO) {
        ventasDao.getVentasConClientesYProductos(clienteId, productoId)
    }

    suspend fun getVentaById(ventaId: Int): Venta? = withContext(Dispatchers.IO) {
        ventasDao.getVentaById(ventaId)
    }

    suspend fun getAllVentasConNombres(): List<Venta> = withContext(Dispatchers.IO) {
        ventasDao.getAllVentasConNombres() // Llama al nuevo método del DAO
    }
}
