package com.asiman.cryptotracker.data.network.model

data class PriceResponse(
//    val coins: Map<String, CurrencyPOJO>,
    val bitcoin: CurrencyPOJO?,
    val ethereum: CurrencyPOJO?,
    val ripple: CurrencyPOJO?,
)