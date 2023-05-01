package com.asiman.cryptotracker.ui.home

import androidx.navigation.fragment.findNavController
import com.asiman.cryptotracker.databinding.FragmentHomeBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    private val adapter: CoinsAdapter = CoinsAdapter(object : ItemClickListener<CoinWithPrice> {
        override fun onItemClick(item: CoinWithPrice) {
            findNavController().navigate(HomeFragmentDirections.toCoin(item.coin))
        }
    })

    private fun setupAdapter() {
        binding.rvCoins.setAdapter(adapter)
    }

    override fun bindUi(): Unit = with(binding) {
        lifecycleOwner = viewLifecycleOwner
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