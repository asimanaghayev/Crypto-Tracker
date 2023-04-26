package com.asiman.cryptotracker.uikit.view

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.asiman.cryptotracker.R
import com.google.android.material.snackbar.Snackbar


fun View.showSnackbar(
    message: String,
    @DrawableRes background: Int = R.drawable.bg_info,
    duration: Int = Snackbar.LENGTH_LONG,
    actionText: String? = null,
    actionListener: View.OnClickListener? = null,
): Snackbar {
    val snack = Snackbar.make(
        this,
        message,
        duration
    )
    val view = snack.view
    view.background = AppCompatResources.getDrawable(context, background)
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.setMargins(
        resources.getDimension(R.dimen.padding_large).toInt(),
        resources.getDimension(R.dimen.padding_enormous).toInt(),
        resources.getDimension(R.dimen.padding_large).toInt(),
        resources.getDimension(R.dimen.padding_large).toInt()
    )
    view.layoutParams = params
    view.elevation = 4f

    if (duration == Snackbar.LENGTH_INDEFINITE) {
        view.setOnClickListener { snack.dismiss() }
    }

    if (!actionText.isNullOrEmpty()) {
        snack.setActionTextColor(ContextCompat.getColor(context, R.color.yellow_FFD93A))
        snack.setAction(actionText, actionListener)
    }

    snack.show()
    return snack
}