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
import com.example.parcial2desarrollo.Model.Venta
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Model.Producto
import com.example.parcial2desarrollo.Repository.VentasRepository
import com.example.parcial2desarrollo.Repository.ClientesRepository
import com.example.parcial2desarrollo.Repository.ProductosRepository
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasFormScreen(
    navController: NavController,
    ventaId: Int? = null,
    ventasRepository: VentasRepository,
    clientesRepository: ClientesRepository,
    productosRepository: ProductosRepository
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCliente by remember { mutableStateOf<Cliente?>(null) }
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }
    var clientes by remember { mutableStateOf(listOf<Cliente>()) }
    var productos by remember { mutableStateOf(listOf<Producto>()) }
    var total by remember { mutableStateOf(0.0) }
    val fechaVenta = remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        clientes = clientesRepository.getAllClientes()
        productos = productosRepository.getAllProductos()

        ventaId?.let {
            val venta = ventasRepository.getVentaById(it)
            venta?.let {
                cantidad = TextFieldValue(venta.cantidad.toString())
                selectedCliente = clientes.find { cliente -> cliente.id == venta.cliente_id }
                selectedProducto = productos.find { producto -> producto.id == venta.producto_id }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (ventaId != null) "Editar Venta" else "Agregar Venta") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF57C00)) // Naranja
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
            // Dropdown para seleccionar Cliente
            var clienteExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { clienteExpanded = true }) {
                    Text(text = selectedCliente?.nombre ?: "Seleccionar Cliente", color = Color.Black)
                }
                DropdownMenu(
                    expanded = clienteExpanded,
                    onDismissRequest = { clienteExpanded = false }
                ) {
                    clientes.forEach { cliente ->
                        DropdownMenuItem(
                            text = { Text(cliente.nombre) },
                            onClick = {
                                selectedCliente = cliente
                                clienteExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown para seleccionar Producto
            var productoExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { productoExpanded = true }) {
                    Text(text = selectedProducto?.nombre ?: "Seleccionar Producto", color = Color.Black)
                }
                DropdownMenu(
                    expanded = productoExpanded,
                    onDismissRequest = { productoExpanded = false }
                ) {
                    productos.forEach { producto ->
                        DropdownMenuItem(
                            text = { Text(producto.nombre) },
                            onClick = {
                                selectedProducto = producto
                                productoExpanded = false
                                // Actualizar el total al seleccionar producto
                                cantidad.text.toIntOrNull()?.let { qty ->
                                    total = producto.precio * qty
                                }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de cantidad
            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it
                    selectedProducto?.let { producto ->
                        cantidad.text.toIntOrNull()?.let { qty ->
                            // Actualizar el total y verificar stock
                            total = producto.precio * qty
                        }
                    }
                },
                label = { Text("Cantidad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFF57C00),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFFF57C00),
                    cursorColor = Color(0xFFF57C00)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Total a pagar
            Text(
                text = "Total a Pagar: $$total",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val qty = cantidad.text.toIntOrNull()
                        if (selectedCliente != null && selectedProducto != null && qty != null) {
                            val producto = selectedProducto!!
                            if (producto.stock >= qty) {
                                // Actualizar el stock del producto
                                val nuevaVenta = Venta(
                                    id = ventaId ?: 0,
                                    producto_id = producto.id,
                                    cliente_id = selectedCliente!!.id,
                                    cantidad = qty,
                                    fecha = fechaVenta.value
                                )

                                ventasRepository.insert(nuevaVenta)
                                productosRepository.updateStock(producto.id, producto.stock - qty)
                                Toast.makeText(context, "Venta registrada exitosamente", Toast.LENGTH_SHORT).show()
                                navController.navigate("VentasListScreen")
                            } else {
                                Toast.makeText(context, "Stock insuficiente para esta cantidad", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)) // Naranja
            ) {
                Text(text = if (ventaId != null) "Guardar cambios" else "Agregar Venta", color = Color.White)
            }
        }
    }
}

