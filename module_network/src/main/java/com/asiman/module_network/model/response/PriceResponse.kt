package com.asiman.module_network.model.response

import com.asiman.module_network.model.pojo.CurrencyPOJO

data class PriceResponse(
//    val coins: Map<String, CurrencyPOJO>,
    val bitcoin: CurrencyPOJO?,
    val ethereum: CurrencyPOJO?,
    val ripple: CurrencyPOJO?,
) : BaseResponse()