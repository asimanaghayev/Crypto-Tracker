package com.asiman.cryptotracker.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class CoinPrice(

    @Embedded
    val coin: Coin,
    @Relation(
        parentColumn = "id",
        entityColumn = "coinId"
    )
    val price: Price,
)
