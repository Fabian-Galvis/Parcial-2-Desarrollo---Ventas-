package com.example.parcial2desarrollo.Repository

import com.example.parcial2desarrollo.DAO.ClientesDao
import com.example.parcial2desarrollo.Model.Cliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClientesRepository(private val clientesDao: ClientesDao) {
    suspend fun insert(cliente: Cliente): Long = withContext(Dispatchers.IO) {
        clientesDao.insert(cliente)
    }

    suspend fun getAllClientes(): List<Cliente> = withContext(Dispatchers.IO) {
        clientesDao.getAllClientes()
    }

    suspend fun delete(clienteId: Int) = withContext(Dispatchers.IO) {
        clientesDao.delete(clienteId)
    }

    suspend fun update(cliente: Cliente) = withContext(Dispatchers.IO) {
        clientesDao.update(cliente)
    }

    suspend fun getClienteById(clienteId: Int): Cliente? = withContext(Dispatchers.IO) {
        clientesDao.getClienteById(clienteId)
    }
}
