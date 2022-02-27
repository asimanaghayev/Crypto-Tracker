package com.asiman.cryptotracker.ui.coin

import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.ItemHistoryBinding
import com.asiman.cryptotracker.ui.base.adapter.BaseAdapter
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    onCLick: ItemClickListener<CoinPrice>?,
) :
    BaseAdapter.BaseViewHolder<CoinPrice>(binding.root, onCLick) {

    override fun bind(item: CoinPrice, position: Int) {
        super.bind(item, position)
        binding.item = item
    }

}