package com.asiman.cryptotracker.data.network

import com.asiman.cryptotracker.BuildConfig
import com.asiman.cryptotracker.base.Constants
import com.asiman.cryptotracker.base.Constants.Companion.API_URL
import com.asiman.cryptotracker.base.Constants.Companion.API_V3
import com.asiman.cryptotracker.data.network.converterfactory.BigDecimalAdapter
import com.asiman.cryptotracker.data.network.service.CoinsService
import com.asiman.cryptotracker.data.network.service.SimpleService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiInitHelper {
    val simpleService: SimpleService by lazy {
        getClient(Constants.SIMPLE_ENDPOINT).create(SimpleService::class.java)
    }
    val coinsService: CoinsService by lazy {
        getClient(Constants.COINS_ENDPOINT).create(CoinsService::class.java)
    }

    private val okHttpClientBuilder = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    private fun okHttpClient(): OkHttpClient {
        when {
            BuildConfig.DEBUG -> {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(logging)
            }
        }
        return okHttpClientBuilder.build()
    }

    fun getClient(baseUrl: String = API_URL): Retrofit {
        return getClient().newBuilder().baseUrl(baseUrl).build()
    }

    fun getClient(): Retrofit {
        when (retrofit) {
            null -> {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(
                        MoshiConverterFactory.create(
                            Moshi.Builder()
                                .add(BigDecimalAdapter)
                                .add(KotlinJsonAdapterFactory())
                                .build()
                        )
                    )
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient())
                    .baseUrl(API_V3)
                    .build()
            }
        }
        return retrofit as Retrofit
    }
}