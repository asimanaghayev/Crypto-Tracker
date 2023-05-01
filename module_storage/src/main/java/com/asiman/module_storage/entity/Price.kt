package com.asiman.module_storage.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asiman.module_storage.entity.model.Amount
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Price(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val coinId: String,

    var usd: Amount,

    var eur: Amount,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    var date: Long,
) : Parcelable
