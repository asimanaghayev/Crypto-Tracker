package com.asiman.cryptotracker.ui.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.FragmentCoinBinding
import com.asiman.cryptotracker.ui.base.BaseFragment
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinFragment : BaseFragment<CoinViewModel>() {

    private lateinit var binding: FragmentCoinBinding
    override val viewModel: CoinViewModel by viewModels()
    private val args: CoinFragmentArgs by navArgs()

    private val adapter: HistoryAdapter = HistoryAdapter(object : ItemClickListener<CoinPrice> {
        override fun onItemClick(item: CoinPrice) {
            // No click is needed for now
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_coin,
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
        viewModel.coinsHistory.observe(
            viewLifecycleOwner
        ) { adapter.setItems(it) }
    }

    private fun bindUi(): Unit = with(binding) {
        lifecycleOwner = this@CoinFragment
        viewmodel = viewModel
        viewModel.coin.postValue(args.coin)
    }
}