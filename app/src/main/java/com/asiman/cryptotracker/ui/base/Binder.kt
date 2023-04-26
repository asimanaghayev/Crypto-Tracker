package com.asiman.cryptotracker.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias Bind<T> = (LayoutInflater) -> T