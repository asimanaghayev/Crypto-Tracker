package com.asiman.cryptotracker.data.annotations

import androidx.annotation.StringDef
import com.asiman.cryptotracker.data.annotations.CoinType.Companion.BITCOIN
import com.asiman.cryptotracker.data.annotations.CoinType.Companion.ETHEREUM
import com.asiman.cryptotracker.data.annotations.CoinType.Companion.RIPPLE

@StringDef(BITCOIN, ETHEREUM, RIPPLE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class Currency {
    companion object {
        const val USD = "usd"

        const val EUR = "eur"
    }
}