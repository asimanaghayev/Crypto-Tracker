package com.asiman.cryptotracker.support.util

import android.content.Context
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Extension functions of the app.
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Float.round(place: Int = 2): Float {
    return this.toBigDecimal().round(place).toFloat()
}

fun BigDecimal.round(place: Int = 2): BigDecimal {
    return this.setScale(place, RoundingMode.HALF_UP)
}

fun BigDecimal.toAmountText(): String {
    return ("$").plus(this.round().toString())
}