package com.example.parcial2desarrollo.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Repository.ClientesRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesListScreen(navController: NavController, clientesRepository: ClientesRepository) {
    val coroutineScope = rememberCoroutineScope()
    var clientes by remember { mutableStateOf(listOf<Cliente>()) }

    // Cargar la lista de clientes desde el repositorio
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            clientes = clientesRepository.getAllClientes()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Clientes") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1565C0)),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("ClientesFormScreen/null") },
                containerColor = Color(0xFF1565C0) // Azul para el botón flotante
            ) {
                Text("+", color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("main") },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1565C0))
                ) {
                    Text("Inicio", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("ClientesFormScreen/null") },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1565C0))
                ) {
                    Text("Agregar Cliente", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(clientes) { cliente ->
                    ClienteItem(
                        cliente = cliente,
                        onEdit = { navController.navigate("ClientesFormScreen/${cliente.id}") },
                        onDelete = {
                            coroutineScope.launch {
                                clientesRepository.delete(cliente.id) // Enviar solo el ID
                                clientes = clientesRepository.getAllClientes()
                            }
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun ClienteItem(cliente: Cliente, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFEEEEEE)),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .background(Color(0xFFEEEEEE)),
        ) {
            Text(
                text = "Nombre: ${cliente.nombre}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1565C0)
            )
            Text(
                text = "Correo: ${cliente.correo}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Text("Editar", color = Color(0xFF1565C0))
                }
                TextButton(onClick = onDelete) {
                    Text("Eliminar", color = Color.Red)
                }
            }
        }
    }
}
