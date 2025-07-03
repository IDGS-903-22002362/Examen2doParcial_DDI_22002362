package com.lrosas.examen2do.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lrosas.examen2do.data.model.Usuario
import com.lrosas.examen2do.ui.theme.navegation.NavRoutes
import com.lrosas.examen2do.viewmodel.MainViewModel

/**
 * Pantalla que captura y valida los datos personales del usuario.
 *
 * Flujo:
 * 1. El usuario introduce nombre, apellidos, fecha de nacimiento y sexo.
 * 2. El botón **Limpiar** restablece todos los campos a su valor inicial.
 * 3. El botón **Siguiente** crea un objeto [Usuario], lo envía al ViewModel
 *    con [MainViewModel.guardarUsuario] y navega a la pantalla del examen.
 *
 * @param navController Permite la navegación hacia la pantalla QUIZ.
 * @param viewModel     Gestiona el estado global y la persistencia.
 */
@Composable
fun FormScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    /* -------------------------------------------------------------------------
     * Estado local de los controles; Compose recompondrá la UI al modificarse.
     * --------------------------------------------------------------------- */
    var nombre     by remember { mutableStateOf("") }
    var aPaterno   by remember { mutableStateOf("") }
    var aMaterno   by remember { mutableStateOf("") }
    var dia        by remember { mutableStateOf("") }
    var mes        by remember { mutableStateOf("") }
    var anio       by remember { mutableStateOf("") }
    var sexo       by remember { mutableStateOf("M") }   // "M" = Masculino, "F" = Femenino

    /* ------------------- Layout principal ------------------------------------ */
    Column(modifier = Modifier.padding(16.dp)) {

        /* -------- Título ------------------------------------------------------ */
        Text(
            text = "Datos personales",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(12.dp))

        /* -------- Campos de texto: nombre y apellidos ------------------------ */
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = aPaterno,
            onValueChange = { aPaterno = it },
            label = { Text("Apellido paterno") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = aMaterno,
            onValueChange = { aMaterno = it },
            label = { Text("Apellido materno") },
            modifier = Modifier.fillMaxWidth()
        )

        /* -------- Fecha de nacimiento ---------------------------------------- */
        Spacer(Modifier.height(16.dp))
        Text(text = "Fecha de nacimiento")

        /* Fila con día, mes y año — cada uno numérico ------------------------ */
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = dia,
                onValueChange = { dia = it },
                label = { Text("Día") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = mes,
                onValueChange = { mes = it },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = anio,
                onValueChange = { anio = it },
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        /* -------- Selección de sexo ------------------------------------------ */
        Spacer(Modifier.height(16.dp))
        Text(text = "Sexo")
        Row {
            RadioButton(
                selected = sexo == "M",
                onClick = { sexo = "M" }
            )
            Text("Masculino", modifier = Modifier.padding(start = 4.dp))

            Spacer(Modifier.width(12.dp))

            RadioButton(
                selected = sexo == "F",
                onClick = { sexo = "F" }
            )
            Text("Femenino", modifier = Modifier.padding(start = 4.dp))
        }

        /* -------- Botones de acción ------------------------------------------ */
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            /* Botón que borra todos los campos ------------------------------- */
            Button(
                onClick = {
                    nombre = ""
                    aPaterno = ""
                    aMaterno = ""
                    dia = ""
                    mes = ""
                    anio = ""
                    sexo = "M"
                }
            ) {
                Text("Limpiar")
            }

            /* Botón que valida, guarda y navega ------------------------------ */
            Button(
                enabled = listOf(nombre, aPaterno, aMaterno, dia, mes, anio)
                    .all { it.isNotBlank() },
                onClick = {
                    /* Conversión segura de texto a Int ----------------------- */
                    val usuario = Usuario(
                        nombre = nombre,
                        apellidoPaterno = aPaterno,
                        apellidoMaterno = aMaterno,
                        dia   = dia.trim().toInt(),
                        mes   = mes.trim().toInt(),
                        anio  = anio.trim().toInt(),
                        sexo  = sexo
                    )
                    viewModel.guardarUsuario(usuario)
                    navController.navigate(NavRoutes.QUIZ.name)
                }
            ) {
                Text("Siguiente")
            }
        }
    }
}
