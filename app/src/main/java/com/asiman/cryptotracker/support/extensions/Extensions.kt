package com.asiman.cryptotracker.support.extensions

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
