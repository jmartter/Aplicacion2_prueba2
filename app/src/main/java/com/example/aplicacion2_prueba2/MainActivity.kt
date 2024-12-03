package com.example.aplicacion2_prueba2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacion2_prueba2.ui.theme.Aplicacion2_prueba2Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            Aplicacion2_prueba2Theme {
                val navController = rememberNavController()
                val events = remember { mutableStateListOf<Event>() }
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController, events) }
                    composable("register") { RegisterScreen(navController, events) }
                }
            }
        }
    }
}