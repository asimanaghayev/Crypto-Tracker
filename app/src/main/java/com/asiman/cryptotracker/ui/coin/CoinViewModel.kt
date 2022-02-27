package com.asiman.cryptotracker.ui.coin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.asiman.cryptotracker.base.Constants.Companion.LIVE_REFRESH_TIME
import com.asiman.cryptotracker.data.db.model.Coin
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.data.repository.CoinsRepository
import com.asiman.cryptotracker.data.repository.SimpleRepository
import com.asiman.cryptotracker.support.tools.NavigationCommand
import com.asiman.cryptotracker.ui.base.BaseViewModel
import com.asiman.cryptotracker.work.PricesSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    application: Application,
    private val repository: SimpleRepository,
    private val coinRepository: CoinsRepository,
) : BaseViewModel(application) {
    companion object {
        const val WORK_NAME = "PRICES_SYNC_WORK"
    }

    private val _coinsHistory: MutableLiveData<List<CoinPrice>> = MutableLiveData<List<CoinPrice>>()
    val coinsHistory: LiveData<List<CoinPrice>> = _coinsHistory

    var coin: MutableLiveData<Coin> = MutableLiveData()
    var coinPrice = MutableLiveData<CoinPrice>()

    var minLimit: MutableLiveData<BigDecimal> = MutableLiveData<BigDecimal>()
    var maxLimit: MutableLiveData<BigDecimal> = MutableLiveData<BigDecimal>()

    init {
        coin.observeForever { coin ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.getPriceHistory(coin).collect { prices ->
                    _coinsHistory.postValue(prices.map { CoinPrice(coin, it) })
                }
            }

            viewModelScope.launch(Dispatchers.IO) {
                handleLoading(true)
                while (true) {
                    delay(LIVE_REFRESH_TIME)
                    coinPrice.postValue(repository.getPrice(coin))
                    handleLoading(false)
                }
            }

            minLimit.postValue(coin.minLimit)
            maxLimit.postValue(coin.maxLimit)
        }
    }

    fun saveClick() {
        coin.value?.let { coin ->
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
            }
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
