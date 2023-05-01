package com.asiman.cryptotracker.base

import android.app.Application
import android.widget.Toast
import com.asiman.cryptotracker.R
import com.asiman.module_network.interceptor.exceptions.ErrorResponseException
import com.asiman.module_network.interceptor.exceptions.UnknownException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalExceptionHandler @Inject constructor(
    private val application: Application,
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is UnknownHostException, is SocketException -> {
                showMessage(application.getString(R.string.network_error_no_internet))
            }

            is SocketTimeoutException -> {
                showMessage(application.getString(R.string.network_error_poor_network))
            }

            is UnknownException -> {
                showMessage(application.getString(R.string.network_error_unknown))
            }

            is ErrorResponseException -> {}
        }
    }

    private fun showMessage(message: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                application,
                message ?: application.getString(R.string.network_error_unknown),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}