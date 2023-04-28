package com.asiman.module_network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.toCoin
import com.asiman.module_storage.AppDatabase
import com.asiman.module_storage.entity.Coin
import com.asiman.module_network.base.ApiInitHelper
import com.asiman.module_storage.dao.CoinDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinsRepository @Inject constructor(
    private val coinDao: CoinDao,
) : BaseRepository() {
    private val service = ApiInitHelper.coinsService

    val localCoins get() = coinDao.getAll()
    private val _allCoins: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>()
    val allCoins: LiveData<List<Coin>> = _allCoins

    fun getCoin(@CoinType coinId: String): Flow<Coin> {
        return coinDao.get(coinId)
    }

    fun getCoinsForAlert(): Flow<List<Coin>> {
        return coinDao.getCoinsForAlert()
    }

    suspend fun syncCoins() {
        localCoins.collect {
            _allCoins.postValue(it as MutableList<Coin>?)
            if (it.isEmpty()) {
                for (coin in CoinType.ALL) {
                    val response = handleRequest { service.getCoinData(coin) }
                    coinDao.insert(response.toCoin())
                    allCoins.value?.plus(response.toCoin())?.let { updatedCoins ->
                        _allCoins.postValue(updatedCoins)
                    }
                }
            }
        }
    }

    fun update(coin: Coin) {
        coinDao.update(coin)
    }
}