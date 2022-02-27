package com.asiman.cryptotracker.data.db.dao

import androidx.room.*
import com.asiman.cryptotracker.data.converters.toPriceList
import com.asiman.cryptotracker.data.db.model.Coin
import com.asiman.cryptotracker.data.db.model.Price
import com.asiman.cryptotracker.data.network.model.PriceResponse
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PriceDao {

    @Query("SELECT * FROM Price WHERE coinId = :coinId")
    abstract fun getPricesForCoin(coinId: String): Flow<List<Price>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(price: Price)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(prices: List<Price>)

    @Update
    abstract fun update(price: Price)

    @Query("delete FROM Price WHERE coinId = :coinId")
    abstract fun delete(coinId: String)

    @Query("delete FROM Price")
    abstract fun deleteAll()

    @Transaction
    open suspend fun replaceAll(items: List<Price>) {
        deleteAll()
        insertAll(items)
    }

    @Transaction
    open suspend fun insert(coins: List<Coin>, response: PriceResponse) {
        insertAll(response.toPriceList(coins))
    }

}