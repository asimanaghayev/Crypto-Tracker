package com.asiman.module_storage.dao

import androidx.room.*
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.entity.Price
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceDao {

    @Query("SELECT * FROM Price WHERE coinId = :coinId")
    fun getPricesForCoin(coinId: String): Flow<List<Price>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(price: Price)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(prices: List<Price>)

    @Update
    fun update(price: Price)

    @Query("delete FROM Price WHERE coinId = :coinId")
    fun delete(coinId: String)

    @Query("delete FROM Price")
    fun deleteAll()

    @Transaction
    open suspend fun replaceAll(items: List<Price>) {
        deleteAll()
        insertAll(items)
    }

//    @Transaction
//    open suspend fun insert(coins: List<Coin>, response: PriceResponse) {
//        insertAll(response.toPriceList(coins))
//    }

}