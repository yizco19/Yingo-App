package com.zy.proyecto_final.constant

object PaymentConstant {
    const val ALIPAY = 1
    const val WEIXIN = 2
    const val BANK = 3
    const val CASH = 4
    const val UNIONPAY = 6
    const val WALLET = 7
    const val CARD = 8
    const val PAYPAL = 9
    const val APPLE_PAY = 10
    const val ANDROID_PAY = 11
    const val SAMSUNG_PAY = 12
    const val PAYTM = 13
    const val VENMO = 14

    val paymentMethodsMap = mapOf(
        "ALIPAY" to ALIPAY,
        "WEIXIN" to WEIXIN,
        "BANK" to BANK,
        "CASH" to CASH,
        "UNIONPAY" to UNIONPAY,
        "WALLET" to WALLET,
        "CARD" to CARD,
        "PAYPAL" to PAYPAL,
        "APPLE_PAY" to APPLE_PAY,
        "ANDROID_PAY" to ANDROID_PAY,
        "SAMSUNG_PAY" to SAMSUNG_PAY,
        "PAYTM" to PAYTM,
        "VENMO" to VENMO
    )
}
