package com.lrosas.examen2do.ui.theme.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lrosas.examen2do.ui.theme.screen.*

/**
 * Punto central de la navegación Compose.
 *
 * @param navController Controlador de navegación recibido desde MainActivity.
 * @param viewModel Instancia compartida que gestiona el estado de la app.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: com.lrosas.examen2do.viewmodel.MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME.name        // Ruta inicial al abrir la app
    ) {

        /* -------------------------------------------------------------------------
         * HOME ─ Pantalla de bienvenida. Presenta un botón “Comenzar” que inicia
         *         el flujo completo (FORM → QUIZ → RESULT).
         * ----------------------------------------------------------------------- */
        composable(NavRoutes.HOME.name)   { HomeScreen(navController) }

        /* -------------------------------------------------------------------------
         * FORM ─ Formulario de datos personales. Captura nombre, apellidos,
         *         fecha de nacimiento y sexo. Guarda la información en el ViewModel
         *         y navega a la pantalla de examen.
         * ----------------------------------------------------------------------- */
        composable(NavRoutes.FORM.name)   { FormScreen(navController, viewModel) }

        /* -------------------------------------------------------------------------
         * QUIZ ─ Examen de seis preguntas de opción múltiple. Permite seleccionar
         *         una respuesta por pregunta, calcula la puntuación y avanza a
         *         resultados cuando el usuario pulsa “Terminar”.
         * ----------------------------------------------------------------------- */
        composable(NavRoutes.QUIZ.name)   { QuizScreen(navController, viewModel) }

        /* -------------------------------------------------------------------------
         * RESULT ─ Despliega los resultados: nombre, edad, signo zodiacal chino
         *          (con su imagen) y calificación obtenida en el examen.
         * ----------------------------------------------------------------------- */
        composable(NavRoutes.RESULT.name) { ResultScreen(navController, viewModel) }
    }
}