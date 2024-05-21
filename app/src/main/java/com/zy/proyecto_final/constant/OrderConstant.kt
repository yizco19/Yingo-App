package com.zy.proyecto_final.constant

import com.zy.proyecto_final.R


class OrderConstant {
    companion object {
        val statusMap = mutableMapOf<String, Int>().apply {
            put("",0)
            put("ALL", 0)
            put("PENDING", 1)
            put("PROCESSING", 2)
            put("DELIVERED", 3)
            put("CANCELLED", 4)
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
