package com.asiman.module_network.service

import com.asiman.module_network.extensions.toQueryString
import com.asiman.module_network.model.PriceResponse
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.annotations.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleService {

    @GET("supported_vs_currencies")
    suspend fun getSupportedVsCurrencies(): List<String>

    @GET("price")
    suspend fun getPrice(
        @Query(encoded = true, value = "ids") ids: String = listOf(
            CoinType.BITCOIN,
            CoinType.ETHEREUM,
            CoinType.RIPPLE
        ).toQueryString(),
        @Query(encoded = true, value = "vs_currencies") currencies: String = listOf(
            Currency.USD,
            Currency.EUR
        ).toQueryString(),
        @Query(encoded = true, value = "include_24hr_change") includeDailyChange: Boolean = true,
        @Query(
            encoded = true,
            value = "include_last_updated_at"
        ) includeLastUpdateTime: Boolean = true,
    ): PriceResponse

}