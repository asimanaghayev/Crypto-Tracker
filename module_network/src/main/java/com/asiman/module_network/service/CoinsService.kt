package com.asiman.module_network.service

import com.asiman.module_network.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsService {

    @GET("{coinId}")
    suspend fun getCoinData(@Path("coinId") coin: String): CoinResponse

}