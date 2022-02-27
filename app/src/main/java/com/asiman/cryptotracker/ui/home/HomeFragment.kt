package com.asiman.cryptotracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.FragmentHomeBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {

    private lateinit var binding: FragmentHomeBinding
    override val viewModel: HomeViewModel by viewModels()

    private val adapter: CoinsAdapter = CoinsAdapter(object : ItemClickListener<CoinPrice> {
        override fun onItemClick(item: CoinPrice) {
            findNavController().navigate(HomeFragmentDirections.actionToCoin(item.coin))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.rvCoins.setAdapter(adapter)
        viewModel.prices.observe(
            viewLifecycleOwner
        ) { adapter.setItems(it) }
    }

    private fun bindUi(): Unit = with(binding) {
        lifecycleOwner = this@HomeFragment
        viewmodel = viewModel
    }

    override fun showLoading() {
        binding.rvCoins.showProgress()
    }

    override fun hideLoading() {
        binding.rvCoins.hideProgress()
    }
}