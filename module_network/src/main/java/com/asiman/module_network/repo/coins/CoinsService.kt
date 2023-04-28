package com.asiman.module_network.repo.coins

import com.asiman.module_network.model.BaseResponse
import com.asiman.module_network.model.CoinResponse
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_COIN_DATA
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_LIST
import com.asiman.module_network.repo.coins.CoinsConstants.PATH_MARKET_CHART
import com.asiman.module_network.repo.coins.CoinsConstants.VAL_ID
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsService {

    @GET(PATH_LIST)
    suspend fun getCoinsList(): BaseResponse

    @GET(PATH_COIN_DATA)
    suspend fun getCoinData(@Path(VAL_ID) coin: String): CoinResponse

    @GET(PATH_MARKET_CHART)
    suspend fun getCoinMarketChart(@Path(VAL_ID) coin: String): CoinResponse

}