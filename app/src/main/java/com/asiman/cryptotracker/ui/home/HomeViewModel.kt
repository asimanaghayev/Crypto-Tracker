package com.asiman.cryptotracker.ui.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.base.Constants.Companion.LIVE_REFRESH_TIME
import com.asiman.cryptotracker.data.repository.CoinsRepository
import com.asiman.cryptotracker.data.repository.SimpleRepository
import com.asiman.cryptotracker.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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
