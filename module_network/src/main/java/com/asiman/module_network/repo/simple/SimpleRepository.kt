package com.asiman.module_network.repo.simple

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiman.module_network.repo.base.BaseRepository
import com.asiman.module_storage.dao.PriceDao
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.entity.Price
import com.asiman.module_storage.relation.CoinWithPrice
import com.asiman.module_network.support.extensions.toCoinPriceList
import com.asiman.module_network.support.extensions.toIdQueryString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SimpleRepository @Inject constructor(
    private val pricesDao: PriceDao,
    private val service: SimpleService,
) : BaseRepository() {

    private val _remoteCoinsPrice = MutableLiveData<List<CoinWithPrice>>()
    val remoteCoinsPrice: LiveData<List<CoinWithPrice>> = _remoteCoinsPrice

    suspend fun fetchPrices(coins: List<Coin>) {
        val response = handleRequest { service.getPrice(ids = coins.toIdQueryString()) }
        _remoteCoinsPrice.postValue(response.toCoinPriceList(coins))
    }

    suspend fun getPrices(coins: List<Coin>): List<CoinWithPrice> {
        return handleRequest { service.getPrice(ids = coins.toIdQueryString()) }
            .toCoinPriceList(coins)
    }

    suspend fun getPrice(coin: Coin): CoinWithPrice {
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