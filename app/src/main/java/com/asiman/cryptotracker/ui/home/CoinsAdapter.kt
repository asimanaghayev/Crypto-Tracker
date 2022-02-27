package com.asiman.cryptotracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.asiman.cryptotracker.data.db.model.CoinPrice
import com.asiman.cryptotracker.databinding.ItemCoinBinding
import com.asiman.cryptotracker.ui.base.adapter.BaseAdapter
import com.asiman.cryptotracker.ui.base.adapter.ItemClickListener

class CoinsAdapter(itemClickListener: ItemClickListener<CoinPrice>) :
    BaseAdapter<CoinPrice, BaseAdapter.BaseViewHolder<CoinPrice>>(itemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CoinPrice> {
        return CoinViewHolder(
            ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

}