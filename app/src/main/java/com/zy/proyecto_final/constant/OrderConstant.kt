package com.zy.proyecto_final.constant

class OrderConstant {
    companion object {
        val statusMap = mutableMapOf<String, Int>().apply {
            put("En espera", 1)
            put("En progreso", 2)
            put("Completado", 3)
        }


        const val DEFAULT_STATUS = "En espera"
    }
}