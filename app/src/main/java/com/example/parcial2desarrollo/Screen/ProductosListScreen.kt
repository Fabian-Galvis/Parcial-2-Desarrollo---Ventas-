package com.example.parcial2desarrollo.Screen

import androidx.compose.foundation.background
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
import com.example.parcial2desarrollo.Model.Producto
import com.example.parcial2desarrollo.Repository.ProductosRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosListScreen(navController: NavController, productosRepository: ProductosRepository) {
    val coroutineScope = rememberCoroutineScope()
    var productos by remember { mutableStateOf(listOf<Producto>()) }

    // Cargar la lista de productos desde el repositorio
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            productos = productosRepository.getAllProductos()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Productos") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2E7D32)) // Verde
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("ProductosFormScreen/null") },
                containerColor = Color(0xFF2E7D32) // Verde para el botón flotante
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
                    colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32)) // Verde
                ) {
                    Text("Inicio", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("ProductosFormScreen/null") },
                    colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32)) // Verde
                ) {
                    Text("Agregar Producto", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(productos) { producto ->
                    ProductoItem(
                        producto = producto,
                        onEdit = { navController.navigate("ProductosFormScreen/${producto.id}") },
                        onDelete = {
                            coroutineScope.launch {
                                productosRepository.delete(producto.id)
                                productos = productosRepository.getAllProductos()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, onEdit: () -> Unit, onDelete: () -> Unit) {
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
                text = "Nombre: ${producto.nombre}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF2E7D32) // Verde
            )
            Text(
                text = "Precio: ${producto.precio}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = "Stock: ${producto.stock}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Text("Editar", color = Color(0xFF2E7D32)) // Verde
                }
                TextButton(onClick = onDelete) {
                    Text("Eliminar", color = Color.Red)
                }
            }
        }
    }
}
