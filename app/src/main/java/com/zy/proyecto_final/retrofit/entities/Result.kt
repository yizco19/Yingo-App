package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class Result<T>(
    @SerializedName(("code")) var code: Int?,    // Código de estado de la operación: 0-Éxito, 1-Fracaso
    @SerializedName(("message")) var message: String?,  // Mensaje de información
    @SerializedName(("data")) var data: T?          // Datos de respuesta
) {
    // Método para retornar rápidamente un resultado exitoso con datos de respuesta
    companion object {
        fun <E> success(data: E): Result<E> {
            return Result(0, "Operación exitosa", data)
        }

        // Método para retornar rápidamente un resultado exitoso sin datos de respuesta
        fun success(): Result<Unit> {
            return Result(0, "Operación exitosa", null)
        }

        // Método para retornar rápidamente un resultado de error con un mensaje personalizado
        fun error(message: String): Result<Unit> {
            return Result(1, message, null)
        }
    }
}