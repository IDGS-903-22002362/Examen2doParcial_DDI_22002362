package com.lrosas.examen2do.data.repo


import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.lrosas.examen2do.data.model.Usuario
import kotlinx.coroutines.tasks.await

/**
 * Encapsula todos los accesos a Firebase Firestore (o Realtime Database).
 *
 * Ventajas de centralizar en un repositorio:
 *  - Aísla la lógica de red de la UI (principio de separación de responsabilidades).
 *  - Facilita pruebas unitarias (puedes inyectar un mock de FirebaseRepo).
 *  - Permite migrar fácilmente entre Firestore y RTDB si cambia el requerimiento.
 */
class FirebaseRepo(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Colección donde se guardarán los usuarios que rellenan el formulario
    private val usuariosRef get() = db.collection("usuarios")

    // Colección donde se guardan los resultados de los exámenes
    private val resultadosRef get() = db.collection("resultados")

    /**
     * Guarda (o sobre-escribe) la información del usuario.
     * El documento se indexa por el nombre completo para evitar duplicados accidentales.
     */
    suspend fun saveUsuario(usuario: Usuario) {
        usuariosRef
            .document(usuario.nombreCompleto)
            .set(usuario)
            .await()
    }

    /**
     * Guarda la calificación obtenida por un usuario.
     *
     * @param usuarioId  Identificador del usuario (usamos nombreCompleto para simplificar).
     * @param score      Calificación numérica sobre 10 o porcentaje.
     */
    suspend fun saveResultado(usuarioId: String, score: Int) {
        val data = mapOf(
            "usuarioId" to usuarioId,
            "score" to score,
            "timestamp" to FieldValue.serverTimestamp()
        )
        resultadosRef.add(data).await()
    }


    /** Carga la lista de preguntas desde Firebase (colección "preguntas"). */
    suspend fun loadPreguntas(): List<com.lrosas.examen2do.data.model.Pregunta> {
        val snapshot = db.collection("preguntas").get().await()
        return snapshot.documents.mapNotNull { it.toObject(PreguntaFirestoreDTO::class.java)?.toDomain() }
    }

    /* ---------------- DTO interno para mapear Firestore -> dominio ---------------- */

    private data class PreguntaFirestoreDTO(
        val enunciado: String = "",
        val opciones: List<String> = emptyList(),
        val indiceCorrecto: Int = 0
    ) {
        fun toDomain() = com.lrosas.examen2do.data.model.Pregunta(enunciado, opciones, indiceCorrecto)
    }
}