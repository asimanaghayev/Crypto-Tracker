package com.asiman.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiman.cryptotracker.data.converters.toCoinPriceList
import com.asiman.cryptotracker.data.converters.toIdQueryString
import com.asiman.cryptotracker.data.db.AppDatabase
import com.asiman.cryptotracker.data.db.entity.Coin
import com.asiman.cryptotracker.data.db.entity.Price
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.data.network.ApiInitHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SimpleRepository @Inject constructor(
    database: AppDatabase,
) : BaseRepository() {
    private val service = ApiInitHelper.simpleService

    private val _remoteCoinsPrice = MutableLiveData<List<CoinPrice>>()
    val remoteCoinsPrice: LiveData<List<CoinPrice>> = _remoteCoinsPrice

    private val pricesDao = database.pricesDao

    suspend fun fetchPrices(coins: List<Coin>) {
        val response = handleRequest { service.getPrice(ids = coins.toIdQueryString()) }
        _remoteCoinsPrice.postValue(response.toCoinPriceList(coins))
    }

    suspend fun getPrices(coins: List<Coin>): List<CoinPrice> {
        return handleRequest { service.getPrice(ids = coins.toIdQueryString()) }
            .toCoinPriceList(coins)
    }

    suspend fun getPrice(coin: Coin): CoinPrice {
        val response = handleRequest { service.getPrice(ids = listOf(coin).toIdQueryString()) }
        return response.toCoinPriceList(listOf(coin))[0]
    }

    fun getPriceHistory(coin: Coin): Flow<List<Price>> {
        return pricesDao.getPricesForCoin(coin.id)
    }

    fun insert(price: Price) {
        pricesDao.insert(price)
    }
}