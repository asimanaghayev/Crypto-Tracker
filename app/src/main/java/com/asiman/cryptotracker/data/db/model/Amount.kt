package com.asiman.cryptotracker.data.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asiman.cryptotracker.data.annotations.Currency
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
@Entity
data class Amount(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Currency var currency: String,
    var value: BigDecimal,
    var dailyChange: Float,
) : Parcelable