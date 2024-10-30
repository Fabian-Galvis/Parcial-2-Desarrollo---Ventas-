package com.example.parcial2desarrollo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.example.parcial2desarrollo.Database.ParcialDatabase
import com.example.parcial2desarrollo.Repository.ClientesRepository
import com.example.parcial2desarrollo.Repository.ProductosRepository
import com.example.parcial2desarrollo.Repository.VentasRepository
import com.example.parcial2desarrollo.Screen.Navegacion
import com.example.parcial2desarrollo.ui.theme.Parcial2DesarrolloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos y el repositorio
        val database = ParcialDatabase.getDatabase(this)
        val clientesRepository = ClientesRepository(database.clientesDao())
        val productosRepository = ProductosRepository(database.productosDao())
        val ventasRepository = VentasRepository(database.ventasDao())

        setContent {
            Parcial2DesarrolloTheme {
                // Recordar el NavController
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    // Llamar a la función de navegación
                    Navegacion(navController = navController, clientesRepository = clientesRepository, productosRepository = productosRepository, ventasRepository = ventasRepository)
                }
            }
        }
    }
}


@Composable
fun PrincipalScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ventas Parcial 2",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn-icons-png.flaticon.com/512/4630/4630769.png")
                .crossfade(true)
                .build(),
            contentDescription = "Ver Listado de Productos",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("ProductosListScreen") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn3d.iconscout.com/3d/premium/thumb/lista-de-clientes-7437114-6136746.png")
                .crossfade(true)
                .build(),
            contentDescription = "Ver Listado de Clientes",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("ClientesListScreen") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn-icons-png.flaticon.com/512/4993/4993414.png")
                .crossfade(true)
                .build(),
            contentDescription = "Ver Listado de Ventas",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("VentasListScreen") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn-icons-png.flaticon.com/512/10608/10608872.png")
                .crossfade(true)
                .build(),
            contentDescription = "Agregar Producto",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("ProductosFormScreen/null") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn.icon-icons.com/icons2/20/PNG/256/business_application_addmale_useradd_insert_add_user_client_2312.png")
                .crossfade(true)
                .build(),
            contentDescription = "Agregar Cliente",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("ClientesFormScreen/null") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn-icons-png.flaticon.com/512/1187/1187005.png")
                .crossfade(true)
                .build(),
            contentDescription = "Agregar Venta",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate("VentasFormScreen/null") }
                .height(50.dp),
            error = painterResource(R.drawable.imagen_error)
        )
    }
}
