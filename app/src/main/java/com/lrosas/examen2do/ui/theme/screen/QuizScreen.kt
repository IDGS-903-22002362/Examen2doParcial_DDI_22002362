package com.lrosas.examen2do.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lrosas.examen2do.ui.theme.navegation.NavRoutes
import com.lrosas.examen2do.viewmodel.MainViewModel

/**
 * Pantalla que muestra 6 preguntas de selección única.
 *
 * • La lista de preguntas proviene del ViewModel.
 * • El usuario marca una opción por pregunta.
 * • Al pulsar "Terminar" se calcula la puntuación y se navega a ResultScreen.
 */
@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val preguntas = viewModel.preguntas

    /* State válido para Compose: lista mutable observable */
    val respuestas = remember {
        mutableStateListOf(*IntArray(preguntas.size) { -1 }.toTypedArray())
    }

    /* Scroll + padding para barra de estado */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()          // deja espacio arriba
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        preguntas.forEachIndexed { index, pregunta ->

            Text(text = "${index + 1}. ${pregunta.enunciado}")
            Spacer(Modifier.height(4.dp))

            pregunta.opciones.forEachIndexed { i, opcion ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = respuestas[index] == i,
                            onClick = { respuestas[index] = i }
                        )
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = respuestas[index] == i,
                        onClick = null                // el click lo maneja Row
                    )
                    Text(text = opcion)
                }
            }

            Spacer(Modifier.height(20.dp))            // espacio entre preguntas
        }

        Button(
            enabled = respuestas.none { it == -1 },
            onClick = {
                val score = viewModel.calcularScore(respuestas.toIntArray())
                viewModel.guardarScore(score)
                navController.navigate(NavRoutes.RESULT.name)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Terminar")
        }
    }
}