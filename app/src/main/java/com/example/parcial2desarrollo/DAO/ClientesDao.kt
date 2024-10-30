package com.example.parcial2desarrollo.DAO

import androidx.room.*
import com.example.parcial2desarrollo.Model.Cliente

@Dao
interface ClientesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: Cliente): Long

    @Query("SELECT * FROM clientes")
    suspend fun getAllClientes(): List<Cliente>

    @Query("DELETE FROM clientes WHERE id = :clienteId")
    suspend fun delete(clienteId: Int)

    @Update
    suspend fun update(cliente: Cliente)

    @Query("SELECT * FROM clientes WHERE id = :clienteId LIMIT 1")
    suspend fun getClienteById(clienteId: Int): Cliente?
}
