package com.asiman.cryptotracker.data.annotations

import androidx.annotation.StringDef
import com.asiman.cryptotracker.data.annotations.Currency.Companion.EUR
import com.asiman.cryptotracker.data.annotations.Currency.Companion.USD

@StringDef(USD, EUR)
@Retention(AnnotationRetention.SOURCE)
annotation class Currency {
    companion object {
        const val USD = "usd"

        const val EUR = "eur"
    }
}