package com.asiman.cryptotracker.ui.home

import com.asiman.cryptotracker.databinding.ItemCoinBinding
import com.example.module_ui_kit.view.recyclerview.BaseAdapter
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice

class CoinViewHolder(
    private val binding: ItemCoinBinding,
    onCLick: ItemClickListener<CoinWithPrice>?,
) :
    BaseAdapter.BaseViewHolder<CoinWithPrice>(binding.root, onCLick) {

    override fun bind(item: CoinWithPrice, position: Int) {
        super.bind(item, position)
        binding.item = item
    }

}