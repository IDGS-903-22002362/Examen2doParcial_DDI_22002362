package com.lrosas.examen2do.data.model

/**
 * Representa los datos personales capturados en la pantalla de formulario.
 *
 * @property nombre Nombre(s) de pila.
 * @property apellidoPaterno Primer apellido.
 * @property apellidoMaterno Segundo apellido.
 * @property dia Día de nacimiento (1-31).
 * @property mes Mes de nacimiento (1-12).  Se usa el formato numérico para simplificar.
 * @property anio Año de nacimiento (4 dígitos).
 * @property sexo Sexo biológico seleccionado por el usuario: "M" = Masculino, "F" = Femenino.
 *
 * Notas de uso:
 *  - La clase es `data class`, por lo tanto genera automáticamente `equals`, `hashCode` y `copy`.
 *  - Incluye dos propiedades calculadas (`nombreCompleto` y `edad`) que NO se almacenan
 *    en Firebase, pero resultan útiles en la capa de UI.
 */
data class Usuario(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val dia: Int,
    val mes: Int,
    val anio: Int,
    val sexo: String
) {
    /** Nombre completo con apellidos en mayúsculas para su despliegue o búsquedas posteriores. */
    val nombreCompleto: String
        get() = "$nombre ${apellidoPaterno.uppercase()} ${apellidoMaterno.uppercase()}"

    /**
     * Edad calculada dinámicamente a partir de la fecha actual.
     */
    val edad: Int
        get() {
            val nacimiento = java.time.LocalDate.of(anio, mes, dia)
            return java.time.Period.between(nacimiento, java.time.LocalDate.now()).years
        }
}
