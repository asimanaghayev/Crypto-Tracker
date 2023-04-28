package com.asiman.module_network.repo.coins

import com.asiman.module_network.base.NetworkConstants.API_V3

object CoinsConstants {
    private const val PATH_COINS = API_V3 + "coins/"

    const val VAL_ID = "{id}"

    const val PATH_LIST = "${PATH_COINS}list"
    const val PATH_COIN_DATA = "${PATH_COINS}${VAL_ID}"
    const val PATH_MARKET_CHART = "${PATH_COINS}${VAL_ID}/market_chart"
}