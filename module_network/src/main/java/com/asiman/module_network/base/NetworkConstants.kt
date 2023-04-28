package com.asiman.module_network.base

import com.asiman.module_network.BuildConfig

object NetworkConstants {
    const val BASE_URL: String = BuildConfig.SERVER_URL
    const val API_URL: String = "$BASE_URL/api"
    const val API_V3: String = "$API_URL/v3/"

    const val SIMPLE_ENDPOINT = API_V3 + "simple/"
    const val COINS_ENDPOINT = API_V3 + "coins/"
}