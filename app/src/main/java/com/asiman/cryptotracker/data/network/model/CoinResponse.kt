package com.asiman.cryptotracker.data.network.model

data class CoinResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val description: LocalesPOJO,
    val image: ImagesPOJO,
): BaseResponse()