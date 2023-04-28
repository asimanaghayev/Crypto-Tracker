package com.asiman.cryptotracker.ui.coin

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.entity.Coin
import com.asiman.module_network.repository.CoinsRepository
import com.asiman.module_network.repository.SimpleRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal


@RunWith(MockitoJUnitRunner::class)
class CoinViewModelTest {

    @Mock
    private lateinit var simpleRepository: SimpleRepository

    @Mock
    private lateinit var coinsRepository: CoinsRepository

    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewmodel: CoinViewModel
    private val coin = Coin(CoinType.BITCOIN, "btc", "Bitcoin", "")

    @Before
    fun setup() {
        viewmodel = CoinViewModel(Application(), simpleRepository, coinsRepository)

        whenever(viewmodel.coin.value)
            .thenReturn(coin)
    }

    @Test
    fun whenSaving_withoutLimits_shouldNotStartWorker() {
        whenever(viewmodel.minLimit.value)
            .thenReturn(BigDecimal.ZERO)

        viewmodel.saveClick()
        verify(viewmodel, times(0)).initWorker()
    }

    @Test
    fun whenSaving_withoutMinLimit_shouldStartWorker() {
        whenever(viewmodel.minLimit.value)
            .thenReturn(BigDecimal.ONE)

        viewmodel.saveClick()
        verify(viewmodel, times(1)).initWorker()
    }

    @Test
    fun whenSaving_withoutMaxLimit_shouldStartWorker() {
        whenever(viewmodel.maxLimit.value)
            .thenReturn(BigDecimal.ONE)

        viewmodel.saveClick()
        verify(viewmodel, times(1)).initWorker()
    }
}