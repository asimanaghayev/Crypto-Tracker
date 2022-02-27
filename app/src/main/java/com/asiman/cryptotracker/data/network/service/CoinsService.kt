package com.asiman.cryptotracker.data.network.service

import com.asiman.cryptotracker.data.network.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsService {

    @GET("{coinId}")
    suspend fun getCoinData(@Path("coinId") coin: String): CoinResponse

}