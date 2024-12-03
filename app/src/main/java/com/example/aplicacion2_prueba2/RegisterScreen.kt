package com.example.aplicacion2_prueba2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun RegisterScreen(navController: NavHostController, events: MutableList<Event>) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF87CEEB)) // Azul cielo
                    .height(56.dp)
            ) {
                Text(
                    text = "Registro de Eventos",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(value = title, onValueChange = { title = it }, label = { Text("Nombre") })
            TextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
            TextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
            TextField(value = date, onValueChange = { date = it }, label = { Text("Fecha") })
            TextField(value = capacity, onValueChange = { capacity = it }, label = { Text("Aforo") })
            TextField(value = address, onValueChange = { address = it }, label = { Text("Dirección") })
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Cerrar")
                }
                Button(onClick = {
                    if (capacity.toIntOrNull() == null) {
                        Toast.makeText(context, "El aforo debe ser un número", Toast.LENGTH_SHORT).show()
                    } else if (price != "gratis" && price.toDoubleOrNull() == null) {
                        Toast.makeText(context, "El precio debe ser un número o 'gratis'", Toast.LENGTH_SHORT).show()
                    } else if (price != "gratis" && price.toDouble() < 0) {
                        Toast.makeText(context, "El precio no puede ser negativo", Toast.LENGTH_SHORT).show()
                    } else {
                        events.add(Event(title, description, price, date, capacity.toInt(), address))
                        navController.popBackStack()
                    }
                }) {
                    Text("Guardar")
                }
            }
        }
    }
}