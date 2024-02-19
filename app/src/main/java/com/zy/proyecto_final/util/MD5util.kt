package com.zy.proyecto_final.util

import java.math.BigInteger
import java.security.MessageDigest

class MD5util {
    fun getMD5(texto: String): String {
        val md = MessageDigest.getInstance("MD5")
        val byteArray = md.digest(texto.toByteArray())

        val numeroEnHexadecimal = BigInteger(1, byteArray)
        var hashMD5 = numeroEnHexadecimal.toString(16)

        while (hashMD5.length < 32) {
            hashMD5 = "0$hashMD5"
        }
        return hashMD5
    }
}
