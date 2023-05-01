package com.asiman.cryptotracker.ui.coin.chart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.asiman.cryptotracker.ui.base.BaseViewModel
import com.asiman.module_network.model.pojo.OhlcPOJO
import com.asiman.module_network.repo.coins.CoinsRepository
import com.asiman.module_storage.annotations.ChartRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    application: Application,
    private val coinRepository: CoinsRepository,
) : BaseViewModel(application, coinRepository) {

    private val _chart: MutableLiveData<List<OhlcPOJO>> = MutableLiveData<List<OhlcPOJO>>()
    val chart: LiveData<List<OhlcPOJO>> = _chart

    fun fetchOhlc(coinId: String, @ChartRange type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _chart.postValue(coinRepository.getCoinOhlc(coinId, type))
        }
    }
}
