package com.example.module_ui_kit.view.recyclerview

interface MultiClickListener<T> {
    fun onItemClick(t: T, position: Int, clickType: Int)
}