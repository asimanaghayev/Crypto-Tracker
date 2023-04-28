package com.asiman.cryptotracker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asiman.cryptotracker.R
import com.asiman.module_storage.AppDatabase
import com.asiman.module_network.repository.CoinsRepository
import com.asiman.module_network.repository.SimpleRepository
import com.asiman.cryptotracker.support.extensions.sendNotification
import com.asiman.cryptotracker.support.util.toAmountText
import java.math.BigDecimal

class PricesSyncWorker(
    val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val coinsRepository = CoinsRepository(AppDatabase.getCoinDao(context))
        val repository = SimpleRepository(AppDatabase.getPriceDao(context))

        coinsRepository.getCoinsForAlert().collect {
            val coinPrices = repository.getPrices(it)
            for (coinPrice in coinPrices) {
                with(coinPrice) {
                    repository.insert(price)

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