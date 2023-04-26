package com.asiman.cryptotracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asiman.cryptotracker.base.Constants
import com.asiman.cryptotracker.data.db.converters.Converters
import com.asiman.cryptotracker.data.db.dao.CoinDao
import com.asiman.cryptotracker.data.db.dao.PriceDao
import com.asiman.cryptotracker.data.db.entity.Amount
import com.asiman.cryptotracker.data.db.entity.Coin
import com.asiman.cryptotracker.data.db.entity.Price


@Database(version = 1 , entities = [Coin::class, Amount::class, Price::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val pricesDao: PriceDao
    abstract val coinDao: CoinDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: AppDatabase

        fun getDatabase(
            context: Context,
        ): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}