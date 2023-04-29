package com.asiman.module_storage.converters

import androidx.room.TypeConverter
import com.asiman.module_storage.entity.model.Amount
import com.google.gson.Gson
import java.math.BigDecimal
import java.sql.Date

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
        return value?.let {
            BigDecimal(it).divide(BigDecimal(100))
        }
    }

    @TypeConverter
    fun toLong(value: BigDecimal?): Long? {
        return value?.multiply(BigDecimal(100))?.toLong()
    }

    @TypeConverter
    fun amountToJson(value: Amount?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToAmount(value: String?): Amount? {
        return if (value == null) null
        else Gson().fromJson(value, Amount::class.java)
    }
}
