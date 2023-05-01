package com.asiman.cryptotracker.ui.coin

import androidx.navigation.fragment.navArgs
import com.asiman.cryptotracker.databinding.FragmentCoinBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.module_storage.relation.CoinWithPrice
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinFragment : BaseFragment<FragmentCoinBinding, CoinViewModel>(
    FragmentCoinBinding::inflate, CoinViewModel::class.java
) {
    private val args: CoinFragmentArgs by navArgs()

    private val adapter: HistoryAdapter = HistoryAdapter(object : ItemClickListener<CoinWithPrice> {
        override fun onItemClick(item: CoinWithPrice) {
            // No click is needed for now
        }
    })
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun bindUi(): Unit = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        viewmodel = viewModel

        setupAdapter()
        setupPager()
        viewModel.setupCoin(args.coin)
    }

    private fun setupPager() {
        pagerAdapter = ViewPagerAdapter(requireActivity(), args.coin)
        binding.vpChart.adapter = pagerAdapter

        TabLayoutMediator(binding.chartTabs, binding.vpChart) { tab, position ->
            tab.text = pagerAdapter.getTabName(position)
        }.attach()
    }

    private fun setupAdapter() {
        binding.rvHistory.setAdapter(adapter)
    }

    override fun bindObservers() {
        viewModel.coinsHistory.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }
}