package com.example.module_ui_kit.view

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.module_ui_kit.R

class LoadingView constructor(
    private val activity: AppCompatActivity,
) {
    companion object {
        private const val LOADING_TIMEOUT = 15000L
        private const val LOADING_DISMISS_DELAY = 300L
    }

    private var loadingDialog = Dialog(activity).apply {
        setOnKeyListener { _: DialogInterface, keyCode: Int, keyEvent: KeyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                activity.onBackPressed()
                dismiss()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.view_loading)
    }

    fun showLoading() {
        loadingDialog.let {
            try {
                if (!it.isShowing) {
                    it.show()
                    Handler(Looper.getMainLooper()).postDelayed({ hideLoading() }, LOADING_TIMEOUT)
                }
            } catch (ignored: Exception) {
            }
        }
    }

    fun hideLoading() {
        loadingDialog.let {
            if (it.isShowing) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        try {
                            it.dismiss()
                        } catch (ignored: Exception) {
                        }
                    },
                    LOADING_DISMISS_DELAY
                )
            }
        }
    }
}