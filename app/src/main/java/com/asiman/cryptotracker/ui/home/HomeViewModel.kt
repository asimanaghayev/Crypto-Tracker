package com.asiman.cryptotracker.ui.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.base.Constants.LIVE_REFRESH_TIME
import com.asiman.cryptotracker.ui.base.BaseViewModel
import com.asiman.module_network.repo.coins.CoinsRepository
import com.asiman.module_network.repo.simple.SimpleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val repository: SimpleRepository,
    private val coinsRepository: CoinsRepository,
) : BaseViewModel(application) {

    val prices = repository.remoteCoinsPrice.apply {
        viewModelScope.launch(Dispatchers.IO) {
            coinsRepository.localCoins.collect {
                handleLoading(true)
                while (true) {
                    delay(LIVE_REFRESH_TIME)
                    coinsRepository.allCoins.value?.let {
                        repository.fetchPrices(it)
                        handleLoading(false)
                    }
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
