package com.asiman.module_network.repo.coins

import com.asiman.module_network.model.response.BaseResponse
import com.asiman.module_network.model.response.CoinResponse
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_COIN_DATA
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_COIN_OHLC
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_LIST
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_MARKET_CHART
import com.asiman.module_network.repo.coins.CoinsConstants.VAL_ID
import com.asiman.module_storage.annotations.ChartRange
import com.asiman.module_storage.annotations.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsService {

    @GET(PATH_LIST)
    suspend fun getCoinsList(): Response<BaseResponse>

    @GET(PATH_COIN_DATA)
    suspend fun getCoinData(@Path(VAL_ID) coin: String): Response<CoinResponse>

    @GET(PATH_COIN_OHLC)
    suspend fun getCoinOhlc(
        @Path(VAL_ID) coin: String,
        @Query(encoded = true, value = CoinsConstants.QUERY_CURRENCY)
        currency: String = Currency.USD,
        @Query(encoded = true, value = CoinsConstants.QUERY_DAYS)
        days: String = ChartRange.DAY,
    ): List<List<Float>>

    @GET(PATH_MARKET_CHART)
    suspend fun getCoinMarketChart(@Path(VAL_ID) coin: String): Response<CoinResponse>

}