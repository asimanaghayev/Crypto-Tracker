package com.asiman.cryptotracker.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.base.Constants.Companion.LIVE_REFRESH_TIME
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.data.repository.CoinsRepository
import com.asiman.cryptotracker.data.repository.SimpleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    application: Application,
    private val repository: SimpleRepository,
    private val coinsRepository: CoinsRepository,
) : AndroidViewModel(application) {

    val prices: LiveData<List<CoinPrice>> = repository.remoteCoinsPrice

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coinsRepository.localCoins.collect {
                coinsRepository.syncCoins()

                while (true) {
                    delay(LIVE_REFRESH_TIME)
                    if (it.isNotEmpty()) {
                        repository.fetchPrices(it)
                    }
                }
            }
        }
    }
}
