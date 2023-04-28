package com.asiman.cryptotracker.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.base.Constants.LIVE_REFRESH_TIME
import com.asiman.module_network.repo.coins.CoinsRepository
import com.asiman.module_network.repo.simple.SimpleRepository
import com.asiman.module_storage.relation.CoinWithPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    application: Application,
    private val repository: SimpleRepository,
    private val coinsRepository: CoinsRepository,
) : AndroidViewModel(application) {

    val prices: LiveData<List<CoinWithPrice>> = repository.remoteCoinsPrice

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
