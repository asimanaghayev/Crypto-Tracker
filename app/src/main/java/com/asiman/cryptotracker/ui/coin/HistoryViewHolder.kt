package com.asiman.cryptotracker.ui.coin

import com.asiman.cryptotracker.databinding.ItemHistoryBinding
import com.example.module_ui_kit.view.recyclerview.BaseAdapter
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    onCLick: ItemClickListener<CoinWithPrice>?,
) :
    BaseAdapter.BaseViewHolder<CoinWithPrice>(binding.root, onCLick) {

    override fun bind(item: CoinWithPrice, position: Int) {
        super.bind(item, position)
        binding.item = item
    }

}