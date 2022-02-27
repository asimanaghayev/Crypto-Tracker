package com.asiman.cryptotracker.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.base.Constants.Companion.LIVE_REFRESH_TIME
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

    private val coins = coinsRepository.localCoins
    val prices = repository.remoteCoinsPrice.apply {
        viewModelScope.launch(Dispatchers.IO) {
            coinsRepository.localCoins.collect {
                while (true) {
                    delay(LIVE_REFRESH_TIME)
                    repository.fetchPrices(it)
                }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coinsRepository.syncCoins()
        }
    }

}
