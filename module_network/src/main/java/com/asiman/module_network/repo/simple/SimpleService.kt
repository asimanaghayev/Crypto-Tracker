package com.asiman.module_network.repo.simple

import com.asiman.module_network.extensions.toQueryString
import com.asiman.module_network.model.PriceResponse
import com.asiman.module_network.repo.simple.SimpleConstants.PATH_PRICE
import com.asiman.module_network.repo.simple.SimpleConstants.PATH_SUPPORTED_CURRENCIES
import com.asiman.module_network.repo.simple.SimpleConstants.QUERY_CURRENCIES
import com.asiman.module_network.repo.simple.SimpleConstants.QUERY_IDS
import com.asiman.module_network.repo.simple.SimpleConstants.QUERY_INCLUDE_24H_CHANGE
import com.asiman.module_network.repo.simple.SimpleConstants.QUERY_INCLUDE_LAST_UPDATE
import com.asiman.module_storage.annotations.CoinType
import com.asiman.module_storage.annotations.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleService {

    @GET(PATH_SUPPORTED_CURRENCIES)
    suspend fun getSupportedVsCurrencies(): List<String>

    @GET(PATH_PRICE)
    suspend fun getPrice(
        @Query(encoded = true, value = QUERY_IDS)
        ids: String = CoinType.ALL.toQueryString(),
        @Query(encoded = true, value = QUERY_CURRENCIES)
        currencies: String = listOf(Currency.USD, Currency.EUR).toQueryString(),
        @Query(encoded = true, value = QUERY_INCLUDE_24H_CHANGE)
        includeDailyChange: Boolean = true,
        @Query(encoded = true, value = QUERY_INCLUDE_LAST_UPDATE)
        includeLastUpdateTime: Boolean = true,
    ): PriceResponse

}