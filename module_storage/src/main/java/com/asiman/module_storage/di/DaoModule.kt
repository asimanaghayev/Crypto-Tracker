package com.asiman.module_storage.di

import com.asiman.module_storage.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun providePriceDao(database: AppDatabase) = database.priceDao

    @Singleton
    @Provides
    fun provideCoinDao(database: AppDatabase) = database.coinDao

}