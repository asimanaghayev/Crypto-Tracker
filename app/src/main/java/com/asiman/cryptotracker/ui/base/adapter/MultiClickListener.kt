package com.asiman.cryptotracker.ui.base.adapter

interface MultiClickListener<T> {
    fun onItemClick(t: T, position: Int, clickType: Int)
}