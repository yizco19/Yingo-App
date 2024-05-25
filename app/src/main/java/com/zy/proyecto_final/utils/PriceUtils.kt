package com.zy.proyecto_final.utils

import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.Offer

object PriceUtils {
    fun calculateDiscountedPrice(product: Product, offer: Offer?): Double {
        return if (offer != null) {
            product.price?.times((1 - (offer.discount?.div(100)!!))) ?: 0.0
        } else {
            product.price ?: 0.0
        }
    }
}