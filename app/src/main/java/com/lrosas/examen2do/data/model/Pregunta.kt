package com.lrosas.examen2do.data.model

/**
 * Modelo para las preguntas de opción múltiple que se muestran en la pantalla del examen.
 *
 * @property enunciado Texto de la pregunta.
 * @property opciones Lista de cuatro posibles respuestas en el orden que se renderizan.
 * @property indiceCorrecto Índice (0-3) dentro de `opciones` que corresponde a la respuesta correcta.
 *
 * Reglas:
 *  - Siempre deben existir EXACTAMENTE 4 opciones; de lo contrario lanzará `IllegalArgumentException`.
 *  - `indiceCorrecto` debe estar dentro del rango 0..3.
 */
data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val indiceCorrecto: Int
) {
    init {
        require(opciones.size == 4) { "Una pregunta debe tener exactamente 4 opciones." }
        require(indiceCorrecto in 0..3) { "El índice de respuesta correcta debe estar entre 0 y 3." }
    }

    /** Devuelve la respuesta correcta en texto (azúcar sintáctico para la UI o validaciones). */
    val respuestaCorrecta: String get() = opciones[indiceCorrecto]
}
