package com.example.module_utility

import java.math.BigDecimal
import java.math.RoundingMode


fun Float.round(place: Int = 2): Float {
    return this.toBigDecimal().round(place).toFloat()
}

fun BigDecimal.round(place: Int = 2): BigDecimal {
    return this.setScale(place, RoundingMode.HALF_UP)
}