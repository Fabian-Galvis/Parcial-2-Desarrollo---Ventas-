package com.example.parcial2desarrollo.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parcial2desarrollo.PrincipalScreen
import com.example.parcial2desarrollo.Repository.ClientesRepository
import com.example.parcial2desarrollo.Repository.ProductosRepository
import com.example.parcial2desarrollo.Repository.VentasRepository


@Composable
fun Navegacion(navController: NavHostController, clientesRepository: ClientesRepository, productosRepository: ProductosRepository, ventasRepository: VentasRepository) {
    NavHost(navController = navController, startDestination = "main") {

        composable("main") { PrincipalScreen(navController) }
        composable("ProductosListScreen") {
            ProductosListScreen(navController = navController, productosRepository = productosRepository)
        }
        composable("ClientesListScreen") {
            ClientesListScreen(navController = navController, clientesRepository = clientesRepository)
        }
        composable("VentasListScreen") {
            VentasListScreen(navController = navController, ventasRepository = ventasRepository)
        }
        composable("ProductosFormScreen/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId")?.toIntOrNull()
            ProductosFormScreen(navController = navController, productoId = productoId, productosRepository = productosRepository)
        }
        composable("ClientesFormScreen/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toIntOrNull()
            ClientesFormScreen(navController = navController, clienteId = clienteId, clientesRepository = clientesRepository)
        }
        composable("VentasFormScreen/{ventaId}") { backStackEntry ->
            val ventaId = backStackEntry.arguments?.getString("ventaId")?.toIntOrNull()
            VentasFormScreen(navController = navController, ventaId = ventaId, ventasRepository = ventasRepository, clientesRepository = clientesRepository, productosRepository = productosRepository)
        }

    }
}
