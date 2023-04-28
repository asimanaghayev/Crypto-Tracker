package com.asiman.module_network.di

import com.asiman.module_network.repo.coins.CoinsService
import com.asiman.module_network.repo.simple.SimpleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesSecretQuestionsService(retrofit: Retrofit): SimpleService =
        retrofit.create(SimpleService::class.java)

    @Provides
    @Singleton
    fun providesFaqService(retrofit: Retrofit): CoinsService =
        retrofit.create(CoinsService::class.java)

}