package com.asiman.cryptotracker.ui.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.ItemHistoryBinding
import com.asiman.cryptotracker.ui.base.adapter.BaseAdapter
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener

class HistoryAdapter(itemClickListener: ItemClickListener<CoinPrice>) :
    BaseAdapter<CoinPrice, BaseAdapter.BaseViewHolder<CoinPrice>>(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CoinPrice> {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

}