package com.asiman.module_network.repo.simple

import com.asiman.module_network.base.NetworkConstants.API_V3

object SimpleConstants {
    private const val PATH_SIMPLE = API_V3 + "simple/"

    const val QUERY_IDS = "ids"
    const val QUERY_CURRENCIES = "vs_currencies"
    const val QUERY_INCLUDE_24H_CHANGE = "include_24hr_change"
    const val QUERY_INCLUDE_LAST_UPDATE = "include_last_updated_at"

    const val PATH_PRICE = "${PATH_SIMPLE}price"
    const val PATH_SUPPORTED_CURRENCIES = "${PATH_SIMPLE}supported_vs_currencies"
}