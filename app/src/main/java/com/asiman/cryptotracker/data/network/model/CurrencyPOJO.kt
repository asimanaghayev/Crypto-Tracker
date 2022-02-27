package com.asiman.cryptotracker.data.network.model

import com.squareup.moshi.Json
import java.math.BigDecimal

data class CurrencyPOJO(
    val usd: BigDecimal = BigDecimal.ZERO,
    @Json(name = "usd_24h_change") val usdChangePercent: Float = 0f,

    val eur: BigDecimal = BigDecimal.ZERO,
    @Json(name = "eur_24h_change") val eurChangePercent: Float = 0f,

    @Json(name = "last_updated_at") val updateTime: Long = 0,
)