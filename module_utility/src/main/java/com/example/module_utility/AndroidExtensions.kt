package com.asiman.cryptotracker.support.extensions

import android.content.Context
import android.widget.Toast

/**
 * Extension functions of the app.
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
