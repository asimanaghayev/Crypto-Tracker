package com.asiman.cryptotracker.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.databinding.ActivityMainBinding
import com.example.module_ui_kit.view.LoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    lateinit var loadingView: LoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoadingView()
    }

    private fun setupLoadingView() {
        loadingView = LoadingView(this)
    }

    open fun showLoading() {
        loadingView.showLoading()
    }

    open fun hideLoading() {
        loadingView.hideLoading()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}