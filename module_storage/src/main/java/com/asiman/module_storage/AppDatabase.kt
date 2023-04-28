package com.asiman.module_storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asiman.module_storage.converters.Converters
import com.asiman.module_storage.dao.CoinDao
import com.asiman.module_storage.dao.PriceDao
import com.asiman.module_storage.entity.Amount
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.entity.Price


@Database(version = 1, entities = [Coin::class, Amount::class, Price::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val priceDao: PriceDao
    abstract val coinDao: CoinDao

    companion object {
//        val ALL_MIGRATIONS = arrayOf()

        @Volatile
        private lateinit var INSTANCE: AppDatabase

        fun getDatabase(
            context: Context,
        ): AppDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DbConstants.DATABASE_NAME
                )
//                    .addMigrations(*ALL_MIGRATIONS)
                    .build()
            }
            return INSTANCE
        }
    }
}