package com.asiman.module_network.repo.base

import androidx.lifecycle.MutableLiveData
import com.asiman.module_network.model.BaseResponse
import com.asiman.module_network.model.ErrorStatus
import com.asiman.module_network.support.SingleLiveEvent

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