package com.asiman.cryptotracker.ui.coin

import androidx.navigation.fragment.navArgs
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.FragmentCoinBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinFragment : BaseFragment<FragmentCoinBinding, CoinViewModel>(
    FragmentCoinBinding::inflate,
    CoinViewModel::class.java
) {
    private val args: CoinFragmentArgs by navArgs()

    private val adapter: HistoryAdapter = HistoryAdapter(object : ItemClickListener<CoinPrice> {
        override fun onItemClick(item: CoinPrice) {
            // No click is needed for now
        }
    })

    override fun bindUi(): Unit = with(binding) {
        lifecycleOwner = this@CoinFragment
        viewmodel = viewModel

        setupAdapter()
        viewModel.coin.postValue(args.coin)
    }

    private fun setupAdapter() {
        binding.rvCoins.setAdapter(adapter)
    }

    override fun bindObservers() {
        viewModel.coinsHistory.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }
}