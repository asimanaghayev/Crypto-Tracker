package com.asiman.cryptotracker.data.network.model

open class BaseResponse(
    val status: ErrorStatus? = null,
) {
    fun isSuccess() = status == null
}

data class ErrorStatus(
    val errorCode: Int,
    val errorMessage: String,
)