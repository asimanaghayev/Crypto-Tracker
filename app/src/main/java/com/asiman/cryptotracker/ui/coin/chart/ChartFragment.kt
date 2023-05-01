package com.asiman.cryptotracker.ui.coin.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.databinding.FragmentChartBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.module_network.model.pojo.OhlcPOJO
import com.asiman.module_storage.annotations.ChartRange
import com.asiman.module_storage.entity.Coin
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding, ChartViewModel>(
    FragmentChartBinding::inflate, ChartViewModel::class.java
) {
    private val coinId by lazy { arguments?.getString(ARG_COIN) ?: "" }
    private val type by lazy { arguments?.getString(ARG_CHART_TYPE) ?: ChartRange.DAY }

    override fun bindUi(): Unit = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        viewmodel = viewModel

        viewModel.fetchOhlc(coinId, type)

        binding.candleStickChart.description = Description().apply {
            text = getString(R.string.app_name)
        }
    }

    override fun bindObservers() {
        viewModel.chart.observe(viewLifecycleOwner) { list ->
            setChartData(list)
        }
    }

    private fun setChartData(list: List<OhlcPOJO>) {
        val yValsCandleStick = ArrayList<CandleEntry>()
        yValsCandleStick.addAll(list.mapIndexed { index, it ->
            CandleEntry(
                index.toFloat(), it.high, it.low, it.open, it.close
            )
        })


        val data = CandleData(createDataSet(yValsCandleStick))

        binding.candleStickChart.data = data
        binding.candleStickChart.invalidate()
    }

    private fun createDataSet(yValsCandleStick: java.util.ArrayList<CandleEntry>): CandleDataSet {
        val set = CandleDataSet(yValsCandleStick, "")
        set.color = Color.rgb(80, 80, 80)

        set.shadowColor = requireContext().getColor(com.example.module_ui_kit.R.color.red_F5465D)
        set.shadowWidth = 0.8f
        set.decreasingColor =
            requireContext().getColor(com.example.module_ui_kit.R.color.red_E76A70)
        set.increasingColor = requireContext().getColor(R.color.colorAccent)
        set.increasingPaintStyle = Paint.Style.FILL
        set.neutralColor = Color.LTGRAY
        set.setDrawValues(false)
        return set
    }

    companion object {
        private val ARG_COIN = "coin"
        private val ARG_CHART_TYPE = "chart_type"

        fun newInstance(coin: Coin, @ChartRange type: String): ChartFragment {
            val fragment = ChartFragment()
            val args = Bundle()
            args.putString(ARG_COIN, coin.id)
            args.putString(ARG_CHART_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}