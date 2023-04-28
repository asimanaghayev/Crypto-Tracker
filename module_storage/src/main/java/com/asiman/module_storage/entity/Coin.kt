package com.asiman.module_storage.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
@Entity
data class Coin(
    @PrimaryKey var id: String,
    var symbol: String,
    var name: String,
    var imageUrl: String,
    var minLimit: BigDecimal = BigDecimal(0),
    var maxLimit: BigDecimal = BigDecimal(0),
) : Parcelable