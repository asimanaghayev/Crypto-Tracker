package com.asiman.cryptotracker.ui.coin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.asiman.cryptotracker.ui.coin.chart.ChartFragment
import com.asiman.module_storage.annotations.ChartRange
import com.asiman.module_storage.entity.Coin

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val coin: Coin,
) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val CARD_ITEM_SIZE = 8
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    override fun createFragment(position: Int): Fragment {
        return ChartFragment.newInstance(coin, positionToType(position))
    }

    fun getTabName(position: Int): String {
        return when (positionToType(position)) {
            ChartRange.DAY -> "24H"
            ChartRange.WEEK -> "7d"
            ChartRange.TWO_WEEK -> "14d"
            ChartRange.MONTH -> "30d"
            ChartRange.THREE_MONTH -> "90d"
            ChartRange.SIX_MONTH -> "180d"
            ChartRange.YEAR -> "1y"
            ChartRange.MAX -> "Max"
            else -> ""
        }
    }

    private fun positionToType(position: Int): String {
        return when (position) {
            0 -> ChartRange.DAY
            1 -> ChartRange.WEEK
            2 -> ChartRange.TWO_WEEK
            3 -> ChartRange.MONTH
            4 -> ChartRange.THREE_MONTH
            5 -> ChartRange.SIX_MONTH
            6 -> ChartRange.YEAR
            7 -> ChartRange.MAX
            else -> ChartRange.DAY
        }
    }
}