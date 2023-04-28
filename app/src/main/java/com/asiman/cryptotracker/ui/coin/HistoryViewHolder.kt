package com.asiman.cryptotracker.ui.coin

import com.asiman.cryptotracker.databinding.ItemHistoryBinding
import com.asiman.cryptotracker.ui.base.adapter.BaseAdapter
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener
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