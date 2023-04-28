package com.asiman.module_storage.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.entity.Price

data class CoinWithPrice(

    @Embedded
    val coin: Coin,
    @Relation(
        parentColumn = "id",
        entityColumn = "coinId"
    )
    val price: Price,
)
