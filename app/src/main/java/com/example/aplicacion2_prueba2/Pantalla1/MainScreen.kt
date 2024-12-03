package com.example.aplicacion2_prueba2.Pantalla1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aplicacion2_prueba2.eventos.Event
import com.example.aplicacion2_prueba2.R
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainScreen(navController: NavHostController, events: MutableList<Event>, isEnglish: MutableState<Boolean>) {
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        db.collection("events").get()
            .addOnSuccessListener { result ->
                events.clear()
                for (document in result) {
                    val event = document.toObject(Event::class.java)
                    events.add(event)
                }
            }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF87CEEB)) // Azul cielo
                    .height(56.dp)
            ) {
                Text(
                    text = if (isEnglish.value) "Events" else "Eventos",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                IconButton(
                    onClick = { isEnglish.value = !isEnglish.value }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_language), // Asegúrate de que el recurso de la imagen exista
                        contentDescription = if (isEnglish.value) "Switch to Spanish" else "Cambiar a inglés",
                        tint = Color.Unspecified, // Elimina el tinte para mostrar la imagen original
                        modifier = Modifier.size(24.dp) // Ajusta el tamaño de la imagen
                    )
                }
                    IconButton(
                        onClick = { navController.navigate("register") }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = if (isEnglish.value) "Add Event" else "Agregar Evento", tint = Color.White)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(events) { event ->
                EventItem(event, isEnglish.value)
            }
        }
    }
}
@Composable
fun EventItem(event: Event, isEnglish: Boolean) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.imagen),
            contentDescription = if (isEnglish) "Default Image" else "Imagen predefinida",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = event.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = event.description, fontSize = 16.sp)
            Text(text = "${event.price} ${if (isEnglish) "$" else "€"}", fontSize = 14.sp)
        }
    }
}
