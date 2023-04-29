package com.asiman.module_storage.entity.model

import android.os.Parcelable
import com.asiman.module_storage.annotations.Currency
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
@Parcelize
data class Amount(
    @Currency var currency: String,
    var value: BigDecimal,
    var dailyChange: Float,
) : Parcelable