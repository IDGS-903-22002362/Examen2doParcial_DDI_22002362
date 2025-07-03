package com.lrosas.examen2do.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lrosas.examen2do.viewmodel.MainViewModel

/**
 * Pantalla que presenta nombre, edad, signo zodiacal chino (imagen)
 * y la calificación obtenida en el examen.
 *
 * Los datos se leen del ViewModel tras haberlos calculado previamente.
 */
@Composable
fun ResultScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val usuario = viewModel.usuario
    val score = viewModel.score
    val signo = viewModel.signoChino
    val drawableId = viewModel.signoDrawableId

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hola ${usuario.nombreCompleto}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Tienes ${usuario.edad} años y tu signo zodiacal",
            style = MaterialTheme.typography.bodyLarge
        )
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = signo,
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Es $signo")
        Text(
            text = "Calificación $score",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}