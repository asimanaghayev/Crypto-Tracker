package com.asiman.cryptotracker.data.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asiman.cryptotracker.data.annotations.Currency.Companion.EUR
import com.asiman.cryptotracker.data.annotations.Currency.Companion.USD
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Price(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val coinId: String,

    @Embedded(prefix = USD)
    var usd: Amount,

    @Embedded(prefix = EUR)
    var eur: Amount,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    var date: Long,
) : Parcelable
