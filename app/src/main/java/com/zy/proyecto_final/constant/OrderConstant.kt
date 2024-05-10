package com.zy.proyecto_final.constant


class OrderConstant {
    companion object {
        val statusMap = mutableMapOf<String, Int>().apply {
            put("En espera", 1)
            put("En progreso", 2)
            put("Completado", 3)
            put("Cancelado", 4)
        }

        const val DEFAULT_STATUS = "En espera"
    }
}

fun OrderConstant.Companion.getStatusValue(status: String): Int? {
    return statusMap[status]
}

fun OrderConstant.Companion.getStatusString(status: Int): String? {
    return statusMap.entries.firstOrNull { it.value == status }?.key
}
