package com.asiman.module_storage.annotations

import androidx.annotation.StringDef
import com.asiman.module_storage.annotations.CoinType.Companion.BITCOIN
import com.asiman.module_storage.annotations.CoinType.Companion.ETHEREUM
import com.asiman.module_storage.annotations.CoinType.Companion.RIPPLE

@StringDef(BITCOIN, ETHEREUM, RIPPLE)
@Retention(AnnotationRetention.SOURCE)
annotation class CoinType {
    companion object {
        const val BITCOIN = "bitcoin"

        const val ETHEREUM = "ethereum"

        const val RIPPLE = "ripple"

        val ALL = listOf(BITCOIN, ETHEREUM, RIPPLE)
    }
}