package com.asiman.cryptotracker.data.db.dao

import androidx.room.*
import com.asiman.cryptotracker.data.db.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CoinDao {

    @Query("SELECT * FROM Coin ORDER BY id ASC")
    abstract fun getAll(): Flow<List<Coin>>

    @Query("SELECT * FROM Coin WHERE id==:id")
    abstract fun get(id: String): Flow<Coin>

    @Query("SELECT * FROM Coin WHERE minLimit>0 OR maxLimit>0")
    abstract fun getCoinsForAlert(): Flow<List<Coin>>

    @Query("INSERT INTO Coin (id, symbol, name, imageUrl) VALUES(:id,:symbol,:name,:imageUrl)")
    abstract fun insert(id: String, symbol: String, name: String, imageUrl: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(coin: Coin)

    @Update
    abstract fun update(coin: Coin)
}