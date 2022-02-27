package com.asiman.cryptotracker.ui.home

import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.ItemCoinBinding
import com.asiman.cryptotracker.ui.base.adapter.BaseAdapter
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener

class CoinViewHolder(private val binding: ItemCoinBinding, onCLick: ItemClickListener<CoinPrice>?) :
    BaseAdapter.BaseViewHolder<CoinPrice>(binding.root, onCLick) {

    override fun bind(item: CoinPrice, position: Int) {
        super.bind(item, position)
        binding.item = item
    }

}