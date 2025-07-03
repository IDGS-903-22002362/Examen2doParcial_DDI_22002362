package com.lrosas.examen2do.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lrosas.examen2do.ui.theme.navegation.NavRoutes

/**
 * Pantalla inicial que muestra un botón para iniciar el flujo
 * del formulario–examen-resultado.
 *
 * Navega a la ruta FORM cuando el usuario pulsa "Comenzar".
 */
@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate(NavRoutes.FORM.name) }) {
            Text(text = "Comenzar")
        }
    }
}