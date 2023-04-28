package com.asiman.cryptotracker.ui.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.asiman.cryptotracker.databinding.ItemHistoryBinding
import com.example.module_ui_kit.view.recyclerview.BaseAdapter
import com.example.module_ui_kit.view.recyclerview.ItemClickListener
import com.asiman.module_storage.relation.CoinWithPrice

class HistoryAdapter(itemClickListener: ItemClickListener<CoinWithPrice>) :
    BaseAdapter<CoinWithPrice, BaseAdapter.BaseViewHolder<CoinWithPrice>>(itemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<CoinWithPrice> {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

}