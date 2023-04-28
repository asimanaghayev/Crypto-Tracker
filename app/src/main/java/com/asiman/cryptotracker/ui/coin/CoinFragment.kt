package com.asiman.cryptotracker.ui.coin

import androidx.navigation.fragment.navArgs
import com.asiman.cryptotracker.databinding.FragmentCoinBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinFragment : BaseFragment<FragmentCoinBinding, CoinViewModel>(
    FragmentCoinBinding::inflate,
    CoinViewModel::class.java
) {
    private val args: CoinFragmentArgs by navArgs()

    private val adapter: HistoryAdapter = HistoryAdapter(object : ItemClickListener<CoinWithPrice> {
        override fun onItemClick(item: CoinWithPrice) {
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