package com.asiman.module_network.di

import com.asiman.module_network.BuildConfig
import com.asiman.module_network.base.NetworkConstants.CONNECTION_TIMEOUT
import com.asiman.module_network.base.NetworkConstants.READ_TIMEOUT
import com.asiman.module_network.base.NetworkConstants.WRITE_TIMEOUT
import com.asiman.module_network.converterfactory.BigDecimalAdapter
import com.asiman.module_network.interceptor.NetworkConnectionInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(BigDecimalAdapter)
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun providesDispatcher() = Dispatcher().apply {
        maxRequests = 4
    }

    @Singleton
    @Provides
    fun providesOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        dispatcher: Dispatcher,
    ) = OkHttpClient.Builder().apply {
        dispatcher(dispatcher)
        addInterceptor(networkConnectionInterceptor)

        if (BuildConfig.DEBUG) {
            addInterceptor(loggingInterceptor)
        }

        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    }.build()

}


