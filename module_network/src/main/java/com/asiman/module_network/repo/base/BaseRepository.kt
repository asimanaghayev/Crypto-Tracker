package com.asiman.module_network.repo.base

import com.asiman.module_network.interceptor.error.Errors.UnknownErrorResponse
import com.asiman.module_network.interceptor.exceptions.ErrorResponseException
import com.asiman.module_network.model.response.BaseResponse
import com.asiman.module_network.model.response.ErrorResponse
import com.asiman.module_network.support.SingleLiveEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
open class BaseRepository {
    val operationError = SingleLiveEvent<ErrorResponse>()

    inline fun <T : BaseResponse?> handleRequest(call: () -> Response<T>): T {
        call().apply {
            if (this.isSuccessful) {
                this.body()?.apply {
                    this.status?.let {
                        handleError(it)
                    }
                }
                return this.body() as T
            } else {
                handleErrorResponse((this.errorBody() as ResponseBody).string())
                throw ErrorResponseException()
            }
        }
    }

    fun handleErrorResponse(error: String) {
        try {
            val gson = Gson()
            val type = object : TypeToken<BaseResponse>() {}.type
            val errorResponse: BaseResponse? = gson.fromJson(error, type)
            errorResponse?.status?.let { handleError(it) }
        } catch (e: Exception) {
            handleError(UnknownErrorResponse)
        }
    }

    fun handleError(error: ErrorResponse) {
        operationError.postValue(error)
    }
}