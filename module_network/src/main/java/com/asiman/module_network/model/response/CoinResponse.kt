package com.asiman.module_network.model.response

import com.asiman.module_network.model.pojo.ImagesPOJO
import com.asiman.module_network.model.pojo.LocalesPOJO

data class CoinResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val description: LocalesPOJO,
    val image: ImagesPOJO,
): BaseResponse()