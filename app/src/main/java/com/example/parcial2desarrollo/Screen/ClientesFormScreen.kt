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
import com.example.parcial2desarrollo.Model.Cliente
import com.example.parcial2desarrollo.Repository.ClientesRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesFormScreen(
    navController: NavController,
    clienteId: Int? = null,
    clientesRepository: ClientesRepository
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var correo by remember { mutableStateOf(TextFieldValue("")) }

    // Si clienteId no es null, estamos en modo "Editar"
    LaunchedEffect(clienteId) {
        clienteId?.let {
            val cliente = clientesRepository.getClienteById(it)
            cliente?.let {
                nombre = TextFieldValue(cliente.nombre)
                correo = TextFieldValue(cliente.correo)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (clienteId != null) "Editar Cliente" else "Agregar Cliente") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1565C0)),
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
                    .background (Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1565C0),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF1565C0),
                    cursorColor = Color(0xFF1565C0),
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1565C0),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF1565C0),
                    cursorColor = Color(0xFF1565C0)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (nombre.text.isNotEmpty() && correo.text.isNotEmpty()) {
                            val cliente = Cliente(
                                id = clienteId ?: 0,
                                nombre = nombre.text,
                                correo = correo.text
                            )

                            if (clienteId == null) {
                                // Agregar nuevo cliente
                                clientesRepository.insert(cliente)
                                Toast.makeText(context, "Cliente agregado exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                // Editar cliente existente
                                clientesRepository.update(cliente)
                                Toast.makeText(context, "Cliente editado exitosamente", Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate("ClientesListScreen")
                        } else {
                            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {
                Text(text = if (clienteId != null) "Guardar cambios" else "Agregar Cliente", color = Color.White)
            }
        }
    }
}
