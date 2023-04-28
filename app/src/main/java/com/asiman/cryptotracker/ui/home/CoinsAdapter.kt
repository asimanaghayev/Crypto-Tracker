package com.asiman.cryptotracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.asiman.cryptotracker.databinding.ItemCoinBinding
import com.example.module_ui_kit.view.recyclerview.BaseAdapter
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice

class CoinsAdapter(itemClickListener: ItemClickListener<CoinWithPrice>) :
    BaseAdapter<CoinWithPrice, BaseAdapter.BaseViewHolder<CoinWithPrice>>(itemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<CoinWithPrice> {
        return CoinViewHolder(
            ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

}