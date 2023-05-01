package com.asiman.module_network.repo.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiman.module_network.model.pojo.OhlcPOJO
import com.asiman.module_network.repo.base.BaseRepository
import com.asiman.module_network.support.extensions.asOhlcList
import com.asiman.module_network.support.extensions.toCoin
import com.asiman.module_storage.annotations.ChartRange
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.dao.CoinDao
import com.asiman.module_storage.entity.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinsRepository @Inject constructor(
    private val coinDao: CoinDao,
    private val service: CoinsService,
) : BaseRepository() {

    val localCoins get() = coinDao.getAll()
    private val _allCoins: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>()
    val allCoins: LiveData<List<Coin>> = _allCoins

    fun getCoin(@CoinType coinId: String): Flow<Coin> {
        return coinDao.get(coinId)
    }

    fun getCoinsForAlert(): Flow<List<Coin>> {
        return coinDao.getCoinsForAlert()
    }

    fun update(coin: Coin) {
        coinDao.update(coin)
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

    suspend fun getCoinOhlc(coinId: String, @ChartRange type: String): List<OhlcPOJO> {
        return service.getCoinOhlc(coinId, days = type).asOhlcList()
    }
}