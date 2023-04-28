package com.asiman.module_storage.annotations

import androidx.annotation.StringDef

@StringDef(Currency.USD, Currency.EUR)
annotation class Currency {
    companion object {
        const val USD = "usd"
        const val EUR = "eur"
    }
}