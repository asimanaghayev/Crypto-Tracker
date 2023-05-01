package com.asiman.module_network.support.extensions

import com.asiman.module_network.model.response.CoinResponse
import com.asiman.module_network.model.pojo.CurrencyPOJO
import com.asiman.module_network.model.pojo.OhlcPOJO
import com.asiman.module_network.model.response.PriceResponse
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.annotations.Currency
import com.asiman.module_storage.entity.Coin
import com.asiman.module_storage.entity.Price
import com.asiman.module_storage.entity.model.Amount
import com.asiman.module_storage.relation.CoinWithPrice

/**
 * Extension functions of the app related to the Data.
 */

fun PriceResponse.toCoinPriceList(coins: List<Coin>): List<CoinWithPrice> {
    val result = mutableListOf<CoinWithPrice>()
    for (coin in coins) {
        when (coin.id) {
            CoinType.BITCOIN -> this.bitcoin?.let {
                result.add(CoinWithPrice(coin, it.toPrice(coin)))
            }
            CoinType.ETHEREUM -> this.ethereum?.let {
                result.add(CoinWithPrice(coin, it.toPrice(coin)))
            }
            CoinType.RIPPLE -> this.ripple?.let {
                result.add(CoinWithPrice(coin, it.toPrice(coin)))
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

fun List<List<Float>>.asOhlcList(): List<OhlcPOJO> {
    return map {
        OhlcPOJO(it[0], it[1], it[2], it[3], it[4])
    }
}
