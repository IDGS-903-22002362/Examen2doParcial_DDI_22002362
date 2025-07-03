package com.lrosas.examen2do

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.lrosas.examen2do.ui.theme.navegation.AppNavHost
import com.lrosas.examen2do.viewmodel.MainViewModel

/**
 * Actividad principal que configura Compose, el NavController y el ViewModel.
 *
 * 1. Obtiene [MainViewModel] mediante delegado `viewModels`.
 * 2. Crea un `NavHostController`.
 * 3. Establece el contenido Compose envolviendo el NavHost dentro del `MaterialTheme`.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
