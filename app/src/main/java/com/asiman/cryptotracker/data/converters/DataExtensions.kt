package com.asiman.cryptotracker.data.converters

import com.asiman.cryptotracker.data.annotations.CoinType
import com.asiman.cryptotracker.data.annotations.Currency
import com.asiman.cryptotracker.data.db.model.Amount
import com.asiman.cryptotracker.data.db.model.Coin
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.data.db.model.Price
import com.asiman.cryptotracker.data.network.model.CoinResponse
import com.asiman.cryptotracker.data.network.model.CurrencyPOJO
import com.asiman.cryptotracker.data.network.model.PriceResponse

/**
 * Extension functions of the app related to the Data.
 */

fun PriceResponse.toCoinPriceList(coins: List<Coin>): List<CoinPrice> {
    val result = mutableListOf<CoinPrice>()
    for (coin in coins) {
        when (coin.id) {
            CoinType.BITCOIN -> this.bitcoin?.let {
                result.add(CoinPrice(coin, it.toPrice(coin)))
            }
            CoinType.ETHEREUM -> this.ethereum?.let {
                result.add(CoinPrice(coin, it.toPrice(coin)))
            }
            CoinType.RIPPLE -> this.ripple?.let {
                result.add(CoinPrice(coin, it.toPrice(coin)))
            }
        }
    }
    return result
}

fun PriceResponse.toPriceList(coins: List<Coin>): List<Price> {
    val result = mutableListOf<Price>()
    for (coin in coins) {
        when (coin.id) {
            CoinType.BITCOIN -> this.bitcoin?.let {
                result.add(it.toPrice(coin))
            }
            CoinType.ETHEREUM -> this.ethereum?.let {
                result.add(it.toPrice(coin))
            }
            CoinType.RIPPLE -> this.ripple?.let {
                result.add(it.toPrice(coin))
            }
        }
    }
    return result
}

fun CurrencyPOJO.toPrice(coin: Coin): Price {
    return Price(
        coinId = coin.id,
        usd = Amount(
            currency = Currency.USD,
            dailyChange = this.usdChangePercent,
            value = this.usd
        ),
        eur = Amount(
            currency = Currency.EUR,
            dailyChange = this.eurChangePercent,
            value = this.eur
        ),
        date = this.updateTime
    )
}

fun CurrencyPOJO.toAmountList(@CoinType coinType: String): List<Amount> {
    return mutableListOf(
        Amount(
            currency = Currency.USD,
            value = usd,
            dailyChange = usdChangePercent
        ),
        Amount(
            currency = Currency.EUR,
            value = eur,
            dailyChange = eurChangePercent
        )
    )
}

fun CoinResponse.toCoin(): Coin {
    return Coin(this.id, this.symbol, this.name, this.image.large)
}

fun List<Coin>.toIdQueryString(): String {
    var result = ""
    for (value in this) {
        result = if (result.isEmpty()) {
            result.plus(value.id)
        } else {
            result.plus(",${value.id}")
        }
    }
    return result
}