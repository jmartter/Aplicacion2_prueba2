package com.example.aplicacion2_prueba2.Pantalla2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.aplicacion2_prueba2.eventos.Event
import com.example.aplicacion2_prueba2.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegisterScreen(navController: NavHostController, events: MutableList<Event>, isEnglish: MutableState<Boolean>) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF87CEEB)) // Azul cielo
                    .height(56.dp)
            ) {
                Text(
                    text = if (isEnglish.value) "Event Registration" else "Registro de Eventos",
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(value = title, onValueChange = { title = it }, label = { Text(if (isEnglish.value) "Name" else "Nombre") })
                Image(
                    painter = painterResource(id = R.drawable.imagen),
                    contentDescription = if (isEnglish.value) "Default Image" else "Imagen predefinida",
                    modifier = Modifier.size(64.dp)
                )
            }
            TextField(value = description, onValueChange = { description = it }, label = { Text(if (isEnglish.value) "Description" else "Descripción") })
            TextField(value = address, onValueChange = { address = it }, label = { Text(if (isEnglish.value) "Address" else "Dirección") })
            TextField(value = price, onValueChange = { price = it }, label = { Text(if (isEnglish.value) "Price" else "Precio") })
            TextField(value = date, onValueChange = { date = it }, label = { Text(if (isEnglish.value) "Date (dd-MM-yyyy)" else "Fecha (dd-MM-yyyy)") })
            TextField(value = capacity, onValueChange = { capacity = it }, label = { Text(if (isEnglish.value) "Capacity" else "Aforo") })
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SmallButton(text = if (isEnglish.value) "Close" else "Cerrar", onClick = { navController.popBackStack() })
                Spacer(modifier = Modifier.width(16.dp))
                SmallButton(text = if (isEnglish.value) "Save" else "Guardar", onClick = {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val currentDate = Date()
                    val eventDate: Date?

                    try {
                        eventDate = dateFormat.parse(date)
                        if (eventDate == null || eventDate.before(currentDate)) {
                            Toast.makeText(context, if (isEnglish.value) "The date must be valid and in the future (dd-MM-yyyy)" else "La fecha debe ser válida y posterior a la fecha actual (dd-MM-yyyy)", Toast.LENGTH_SHORT).show()
                            return@SmallButton
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, if (isEnglish.value) "The date must have the format dd-MM-yyyy" else "La fecha debe tener el formato dd-MM-yyyy", Toast.LENGTH_SHORT).show()
                        return@SmallButton
                    }

                    if (capacity.toIntOrNull() == null) {
                        Toast.makeText(context, if (isEnglish.value) "Capacity must be a number" else "El aforo debe ser un número", Toast.LENGTH_SHORT).show()
                    } else if (price != "free" && price.toDoubleOrNull() == null) {
                        Toast.makeText(context, if (isEnglish.value) "Price must be a number or 'free'" else "El precio debe ser un número o 'gratis'", Toast.LENGTH_SHORT).show()
                    } else if (price != "free" && price.toDouble() < 0) {
                        Toast.makeText(context, if (isEnglish.value) "Price cannot be negative" else "El precio no puede ser negativo", Toast.LENGTH_SHORT).show()
                    } else {
                        val event = Event(title, description, price, date, capacity.toInt(), address)
                        db.collection("events").add(event)
                            .addOnSuccessListener {
                                events.add(event)
                                navController.popBackStack()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, if (isEnglish.value) "Error saving the event" else "Error al guardar el evento", Toast.LENGTH_SHORT).show()
                            }
                    }
                })
            }
        }
    }
}
@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), // Gris más fuerte
        shape = RectangleShape, // Forma rectangular
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp, // Elevación moderada
            pressedElevation = 2.dp, // Elevación al presionar
            hoveredElevation = 12.dp // Elevación al pasar el cursor (en escritorio)
        ),
        modifier = Modifier
            .width(150.dp) // Ancho fijo
            .height(48.dp) // Altura de los botones
            .padding(vertical = 8.dp) // Espaciado entre botones
    ) {
        Text(text = text, color = Color.White)
    }
}
