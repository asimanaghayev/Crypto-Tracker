package com.asiman.cryptotracker.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var exceptionHandler: GlobalExceptionHandler

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
    }
}