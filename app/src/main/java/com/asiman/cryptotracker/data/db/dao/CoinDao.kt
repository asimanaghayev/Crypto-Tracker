package com.asiman.cryptotracker.data.db.dao

import androidx.room.*
import com.asiman.cryptotracker.data.db.entity.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM Coin ORDER BY id ASC")
    fun getAll(): Flow<List<Coin>>

    @Query("SELECT * FROM Coin WHERE id==:id")
    fun get(id: String): Flow<Coin>

    @Query("SELECT * FROM Coin WHERE minLimit>0 OR maxLimit>0")
    fun getCoinsForAlert(): Flow<List<Coin>>

    @Query("INSERT INTO Coin (id, symbol, name, imageUrl) VALUES(:id,:symbol,:name,:imageUrl)")
    fun insert(id: String, symbol: String, name: String, imageUrl: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: Coin)

    @Update
    fun update(coin: Coin)
}