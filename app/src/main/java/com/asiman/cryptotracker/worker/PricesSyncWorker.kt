package com.asiman.cryptotracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.support.extensions.sendNotification
import com.asiman.module_network.repo.coins.CoinsRepository
import com.asiman.module_network.repo.simple.SimpleRepository
import com.example.module_ui_kit.support.extensions.toAmountText
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.math.BigDecimal

@HiltWorker
class PricesSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val coinsRepository: CoinsRepository,
    private val simpleRepository: SimpleRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        coinsRepository.getCoinsForAlert().collect {
            val coinPrices = simpleRepository.getPrices(it)
            for (coinPrice in coinPrices) {
                with(coinPrice) {
                    simpleRepository.insert(price)

                    if (coin.maxLimit > BigDecimal.ZERO && price.usd.value > coin.maxLimit) {
                        context.sendNotification(
                            coin.name,
                            context.getString(
                                R.string.notify_up,
                                coin.name,
                                coinPrice.price.usd.value.toAmountText()
                            )
                        )
                    }

                    if (coin.minLimit > BigDecimal.ZERO && price.usd.value < coin.minLimit) {
                        context.sendNotification(
                            coin.name,
                            context.getString(
                                R.string.notify_down,
                                coin.name,
                                coinPrice.price.usd.value.toAmountText()
                            )
                        )
                    }
                }
            }
        }
        return Result.success()
    }
}