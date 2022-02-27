package com.asiman.cryptotracker.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.FragmentHistoryBinding
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import com.asiman.cryptotracker.ui.home.CoinsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()

    private val adapter: CoinsAdapter = CoinsAdapter(object : ItemClickListener<CoinPrice> {
        override fun onItemClick(item: CoinPrice) {
//            findNavController().navigate(HomeFragmentDirections.actionToCoin(item.coin))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_history,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()

        binding.rvCoins.adapter = adapter
        viewModel.prices.observe(
            viewLifecycleOwner
        ) { adapter.setItems(it) }
    }

    private fun bindUi(): Unit = with(binding) {
        lifecycleOwner = this@HistoryFragment
        viewmodel = viewModel
    }
}