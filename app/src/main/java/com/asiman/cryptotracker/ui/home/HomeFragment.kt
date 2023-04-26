package com.asiman.cryptotracker.ui.home

import androidx.navigation.fragment.findNavController
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.FragmentHomeBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val adapter: CoinsAdapter = CoinsAdapter(object : ItemClickListener<CoinPrice> {
        override fun onItemClick(item: CoinPrice) {
            findNavController().navigate(HomeFragmentDirections.toCoin(item.coin))
        }
    })

    private fun setupAdapter() {
        binding.rvCoins.setAdapter(adapter)
    }

    override fun bindUi(): Unit = with(binding) {
        lifecycleOwner = this@HomeFragment
        viewmodel = viewModel

        setupAdapter()
    }

    override fun bindObservers() {
        viewModel.prices.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

    override fun showLoading() {
        binding.rvCoins.showProgress()
    }

    override fun hideLoading() {
        binding.rvCoins.hideProgress()
    }
}