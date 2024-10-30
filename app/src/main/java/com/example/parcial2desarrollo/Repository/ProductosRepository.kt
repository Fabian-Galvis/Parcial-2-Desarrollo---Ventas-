package com.example.parcial2desarrollo.Repository

import com.example.parcial2desarrollo.DAO.ProductosDao
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductosRepository(private val productosDao: ProductosDao) {
    suspend fun insert(producto: Producto): Long = withContext(Dispatchers.IO) {
        productosDao.insert(producto)
    }

    suspend fun getAllProductos(): List<Producto> = withContext(Dispatchers.IO) {
        productosDao.getAllProductos()
    }

    suspend fun delete(productoId: Int) = withContext(Dispatchers.IO) {
        productosDao.delete(productoId)
    }

    suspend fun update(producto: Producto) = withContext(Dispatchers.IO) {
        productosDao.update(producto)
    }
    suspend fun getProductoById(productoId: Int): Producto? = withContext(Dispatchers.IO) {
        productosDao.getProductoById(productoId)
    }
    suspend fun updateStock(productoId: Int, nuevoStock: Int) = withContext(Dispatchers.IO) {
        productosDao.updateStock(productoId, nuevoStock)
    }

}
