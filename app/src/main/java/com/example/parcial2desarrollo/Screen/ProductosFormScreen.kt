package com.example.parcial2desarrollo.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial2desarrollo.Model.Producto
import com.example.parcial2desarrollo.Repository.ProductosRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosFormScreen(
    navController: NavController,
    productoId: Int? = null,
    productosRepository: ProductosRepository
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var stock by remember { mutableStateOf(TextFieldValue("")) }

    // Si productoId no es null, estamos en modo "Editar"
    LaunchedEffect(productoId) {
        productoId?.let {
            val producto = productosRepository.getProductoById(it)
            producto?.let {
                nombre = TextFieldValue(producto.nombre)
                precio = TextFieldValue(producto.precio.toString())
                stock = TextFieldValue(producto.stock.toString())
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (productoId != null) "Editar Producto" else "Agregar Producto") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2E7D32)),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2E7D32),
                    cursorColor = Color(0xFF2E7D32)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2E7D32),
                    cursorColor = Color(0xFF2E7D32)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF2E7D32),
                    cursorColor = Color(0xFF2E7D32)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (nombre.text.isNotEmpty() && precio.text.isNotEmpty() && stock.text.isNotEmpty()) {
                            val producto = Producto(
                                id = productoId ?: 0,
                                nombre = nombre.text,
                                precio = precio.text.toDoubleOrNull() ?: 0.0,
                                stock = stock.text.toIntOrNull() ?: 0
                            )

                            if (productoId == null) {
                                // Agregar nuevo producto
                                productosRepository.insert(producto)
                                Toast.makeText(context, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                // Editar producto existente
                                productosRepository.update(producto)
                                Toast.makeText(context, "Producto editado exitosamente", Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate("ProductosListScreen")
                        } else {
                            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32) // Color verde
                )
            ) {
                Text(text = if (productoId != null) "Guardar cambios" else "Agregar Producto", color = Color.White)
            }
        }
    }
}
