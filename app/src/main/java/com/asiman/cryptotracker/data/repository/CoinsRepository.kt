package com.asiman.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiman.cryptotracker.data.annotations.CoinType
import com.asiman.cryptotracker.data.converters.toCoin
import com.asiman.cryptotracker.data.db.AppDatabase
import com.asiman.cryptotracker.data.db.model.Coin
import com.asiman.cryptotracker.data.network.ApiInitHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CoinsRepository @Inject constructor(
    database: AppDatabase,
) {
    private val service = ApiInitHelper.coinsService
    private val coinDao = database.coinDao

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
            if (it.isNullOrEmpty()) {
                for (coin in CoinType.ALL) {
                    val response = service.getCoinData(coin)
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