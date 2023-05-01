package com.asiman.module_network.model.response

open class BaseResponse(
    val status: ErrorResponse? = null,
) {
    fun isSuccess() = status == null
}
