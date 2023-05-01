package com.asiman.cryptotracker.ui.coin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.asiman.cryptotracker.base.Constants.LIVE_REFRESH_TIME
import com.asiman.cryptotracker.support.tools.NavigationCommand
import com.asiman.cryptotracker.ui.base.BaseViewModel
import com.asiman.cryptotracker.worker.PricesSyncWorker
import com.asiman.module_network.model.pojo.OhlcPOJO
import com.asiman.module_network.repo.coins.CoinsRepository
import com.asiman.module_network.repo.simple.SimpleRepository
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.relation.CoinWithPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    application: Application,
    private val repository: SimpleRepository,
    private val coinRepository: CoinsRepository,
) : BaseViewModel(application, repository, coinRepository) {
    companion object {
        const val WORK_NAME = "PRICES_SYNC_WORK"
    }

    private val _coinsHistory: MutableLiveData<List<CoinWithPrice>> =
        MutableLiveData<List<CoinWithPrice>>()
    val coinsHistory: LiveData<List<CoinWithPrice>> = _coinsHistory

    private val _chart: MutableLiveData<List<OhlcPOJO>> = MutableLiveData<List<OhlcPOJO>>()
    val chart: LiveData<List<OhlcPOJO>> = _chart

    lateinit var coin: Coin
    var coinPrice = MutableLiveData<CoinWithPrice>()

    var minLimit: MutableLiveData<BigDecimal> = MutableLiveData<BigDecimal>()
    var maxLimit: MutableLiveData<BigDecimal> = MutableLiveData<BigDecimal>()

    fun setupCoin(coin: Coin) {
        this.coin = coin
        minLimit.postValue(coin.minLimit)
        maxLimit.postValue(coin.maxLimit)

        fetchCoinHistory()
        syncCoinData()
    }

    private fun fetchCoinHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPriceHistory(coin).collect { prices ->
                _coinsHistory.postValue(prices.map { CoinWithPrice(coin, it) })
            }
        }
    }

    private fun syncCoinData() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(LIVE_REFRESH_TIME)
                coinPrice.postValue(repository.getPrice(coin))
            }
        }
    }

    fun saveClick() {
        minLimit.value?.let {
            coin.minLimit = it
        }

        maxLimit.value?.let {
            coin.maxLimit = it
        }

        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.update(coin)
        }

        if (coin.minLimit > BigDecimal.ZERO || coin.maxLimit > BigDecimal.ZERO) {
            initWorker()
            handleInfo("You will be notified if price exceeds limits.")
        }
        navigate(NavigationCommand.Back)
    }

    fun initWorker() {
        val workManager = getApplication<Application>().let { WorkManager.getInstance(it) }
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val syncWork = PeriodicWorkRequestBuilder<PricesSyncWorker>(
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            syncWork
        )
    }
}
