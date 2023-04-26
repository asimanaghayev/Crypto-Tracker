package com.asiman.cryptotracker.data.db.model

import androidx.room.Embedded
import androidx.room.Relation
import com.asiman.cryptotracker.data.db.entity.Coin
import com.asiman.cryptotracker.data.db.entity.Price

data class CoinPrice(

    @Embedded
    val coin: Coin,
    @Relation(
        parentColumn = "id",
        entityColumn = "coinId"
    )
    val price: Price,
)
