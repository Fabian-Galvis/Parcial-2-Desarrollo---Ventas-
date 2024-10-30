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
import com.example.parcial2desarrollo.Model.Venta
import com.example.parcial2desarrollo.Repository.VentasRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasListScreen(navController: NavController, ventasRepository: VentasRepository) {
    val coroutineScope = rememberCoroutineScope()
    var ventas by remember { mutableStateOf(listOf<Venta>()) }

    // Cargar la lista de ventas desde el repositorio
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            ventas = ventasRepository.getAllVentas()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Ventas") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF57C00)) // Naranja
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("VentasFormScreen/null") },
                containerColor = Color(0xFFF57C00) // Naranja para el botón flotante
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
                    colors = ButtonDefaults.buttonColors(Color(0xFFF57C00)) // Naranja
                ) {
                    Text("Inicio", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("VentasFormScreen/null") },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF57C00)) // Naranja
                ) {
                    Text("Agregar Venta", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(ventas) { venta ->
                    VentaItem(
                        venta = venta,
                        onEdit = { navController.navigate("VentasFormScreen/${venta.id}") },
                        onDelete = {
                            coroutineScope.launch {
                                ventasRepository.delete(venta.id)
                                ventas = ventasRepository.getAllVentas()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VentaItem(venta: Venta, onEdit: () -> Unit, onDelete: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFEEEEEE)),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFFEEEEEE)),
        ) {
            Text(
                text = "ID Producto: ${venta.producto_id}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFF57C00) // Naranja
            )
            Text(
                text = "ID Cliente: ${venta.cliente_id}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = "Cantidad: ${venta.cantidad}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Fecha: ${dateFormat.format(venta.fecha)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Text("Editar", color = Color(0xFFF57C00)) // Naranja
                }
                TextButton(onClick = onDelete) {
                    Text("Eliminar", color = Color.Red)
                }
            }
        }
    }
}
