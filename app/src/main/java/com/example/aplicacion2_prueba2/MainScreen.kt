package com.example.aplicacion2_prueba2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController, events: List<Event>) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF87CEEB)) // Azul cielo
                    .height(56.dp)
            ) {
                Text(
                    text = "Eventos",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
                )
                IconButton(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Event", tint = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(events) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = event.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = event.description, fontSize = 16.sp)
        Text(text = "${event.price} €", fontSize = 14.sp)
    }
}