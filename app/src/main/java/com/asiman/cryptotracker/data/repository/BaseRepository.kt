package com.asiman.cryptotracker.data.repository

import androidx.lifecycle.MutableLiveData
import com.asiman.cryptotracker.data.network.model.BaseResponse
import com.asiman.cryptotracker.data.network.model.ErrorStatus
import com.asiman.cryptotracker.support.tools.SingleLiveEvent

open class BaseRepository {
    val operationError = MutableLiveData<SingleLiveEvent<ErrorStatus>>()

    inline fun <T : BaseResponse?> handleRequest(call: () -> T): T {
        call().apply {
            this?.status?.let {
                val event = SingleLiveEvent<ErrorStatus>()
                event.value = it
                operationError.postValue(event)
            }

            return this
        }
    }
}