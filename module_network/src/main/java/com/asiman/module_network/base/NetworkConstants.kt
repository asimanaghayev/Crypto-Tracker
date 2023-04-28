package com.asiman.module_network.base

import com.asiman.module_network.BuildConfig

object NetworkConstants {
    const val CONNECTION_TIMEOUT = 15L
    const val READ_TIMEOUT = 15L
    const val WRITE_TIMEOUT = 15L

    const val BASE_URL: String = BuildConfig.SERVER_URL
    const val API_URL: String = "$BASE_URL/api"
    const val API_V3: String = "$API_URL/v3/"
}