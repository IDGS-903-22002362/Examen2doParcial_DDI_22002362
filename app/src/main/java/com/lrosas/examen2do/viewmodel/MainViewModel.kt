package com.lrosas.examen2do.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrosas.examen2do.data.model.Pregunta
import com.lrosas.examen2do.data.model.Usuario
import com.lrosas.examen2do.data.repo.FirebaseRepo
import kotlinx.coroutines.launch
import com.lrosas.examen2do.R

/**
 * ViewModel que conserva y administra el estado de la aplicación.
 *
 * Propiedades principales:
 *  • [usuario]  Datos personales capturados en el formulario.
 *  • [preguntas]  Lista fija de seis preguntas de opción múltiple.
 *  • [score]  Calificación obtenida al finalizar el examen.
 *  • [signoChino]  Signo zodiacal chino derivado del año de nacimiento.
 *  • [signoDrawableId]  Recurso gráfico asociado al signo.
 *
 * Métodos públicos:
 *  • [guardarUsuario]  Persiste los datos del usuario y actualiza el estado local.
 *  • [calcularScore]  Determina la calificación a partir de las respuestas.
 *  • [guardarScore]  Persiste la calificación y actualiza el estado local.
 */
class MainViewModel(
    private val repo: FirebaseRepo = FirebaseRepo()
) : ViewModel() {

    /* -------------------- estado -------------------------------------------------- */

    private var _usuario: Usuario? = null
    val usuario: Usuario
        get() = _usuario
            ?: error("El usuario aún no ha sido inicializado mediante guardarUsuario()")

    val preguntas: List<Pregunta> = listOf(
        Pregunta("¿Cuál es la suma de 2 + 2?",
            listOf("8", "6", "4", "3"), 2),
        Pregunta("¿Capital de Francia?",
            listOf("Madrid", "París", "Roma", "Berlín"), 1),
        Pregunta("¿Elemento químico con símbolo H?",
            listOf("Helio", "Hidrógeno", "Mercurio", "Plata"), 1),
        Pregunta("¿Año en que llegó el hombre a la Luna?",
            listOf("1969", "1972", "1958", "1980"), 0),
        Pregunta("¿Resultado de 9 × 7?",
            listOf("63", "56", "72", "49"), 0),
        Pregunta("¿Planeta más cercano al Sol?",
            listOf("Venus", "Mercurio", "Marte", "Júpiter"), 1)
    )

    var score: Int = 0
        private set

    /* -------------------- persistencia ------------------------------------------- */

    fun guardarUsuario(usuario: Usuario) {
        _usuario = usuario
        viewModelScope.launch { repo.saveUsuario(usuario) }
    }

    fun calcularScore(respuestas: IntArray): Int {
        var correctas = 0
        respuestas.forEachIndexed { index, respuesta ->
            if (preguntas[index].indiceCorrecto == respuesta) correctas++
        }
        return (correctas * 10) / preguntas.size
    }

    fun guardarScore(valor: Int) {
        score = valor
        viewModelScope.launch { repo.saveResultado(usuario.nombreCompleto, valor) }
    }

    /* -------------------- utilidades --------------------------------------------- */

    val signoChino: String
        get() = listaSignos[usuario.anio % 12]

    val signoDrawableId: Int
        get() = mapaDrawable[signoChino]
            ?: error("No se encontró drawable para el signo $signoChino")

    private val listaSignos = listOf(
        "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
        "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
    )

    private val mapaDrawable = mapOf(
        "Mono" to R.drawable.mono,
        "Gallo" to R.drawable.gallo,
        "Perro" to R.drawable.perro,
        "Cerdo" to R.drawable.cerdo,
        "Rata" to R.drawable.rata,
        "Buey" to R.drawable.buey,
        "Tigre" to R.drawable.tigre,
        "Conejo" to R.drawable.conejo,
        "Dragón" to R.drawable.dragon,
        "Serpiente" to R.drawable.serpiente,
        "Caballo" to R.drawable.caballo,
        "Cabra" to R.drawable.cabra
    )
}