package com.asiman.module_storage.annotations

import androidx.annotation.StringDef

@StringDef(
    ChartRange.DAY,
    ChartRange.WEEK,
    ChartRange.TWO_WEEK,
    ChartRange.MONTH,
    ChartRange.THREE_MONTH,
    ChartRange.SIX_MONTH,
    ChartRange.YEAR,
    ChartRange.MAX
)
annotation class ChartRange {
    companion object {
        const val DAY = "1"
        const val WEEK = "7"
        const val TWO_WEEK = "14"
        const val MONTH = "30"
        const val THREE_MONTH = "90"
        const val SIX_MONTH = "180"
        const val YEAR = "365"
        const val MAX = "max"
    }
}