package com.asiman.cryptotracker.support.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.asiman.cryptotracker.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.math.BigDecimal
import java.sql.Date
import java.text.SimpleDateFormat


object BindingAdapterUtils {

    @BindingAdapter("amount")
    @JvmStatic
    internal fun TextView.setAmount(amount: BigDecimal?) {
        text = amount?.toAmountText()
    }

    @BindingAdapter("data")
    @JvmStatic
    internal fun TextView.setData(data: Float?) {
        text = data?.round().toString()
    }

    @BindingAdapter("percent")
    @JvmStatic
    internal fun TextView.setPercent(data: Float?) {
        var displayText = ""
        data?.let {
            val percent = it.round()
            when {
                percent < 0 -> {
                    displayText = "▼"
                    setBackgroundResource(R.drawable.bg_percent_red)
                }
                percent > 0 -> {
                    displayText = "▲"
                    setBackgroundResource(R.drawable.bg_percent_green)
                }
                else -> {
                    displayText = ""
                    setBackgroundResource(R.drawable.bg_percent_gray)
                }
            }
            displayText = displayText.plus(percent.toString()).plus("%")
        }
        text = displayText
    }

    @BindingAdapter("date")
    @JvmStatic
    internal fun TextView.setDate(seconds: Long?) {
        seconds?.let {
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
            text = formatter.format(Date(seconds * 1000L))
        }
    }

    @BindingAdapter("min")
    @JvmStatic
    internal fun TextView.setMin(value: BigDecimal?) {
        value?.let {
            if (value > BigDecimal.ZERO) {
                this.visibility = View.VISIBLE
                text = context.getString(R.string.label_limit_min, value.toAmountText())
            }
        }
    }

    @BindingAdapter("max")
    @JvmStatic
    internal fun TextView.setMax(value: BigDecimal?) {
        value?.let {
            if (value > BigDecimal.ZERO) {
                this.visibility = View.VISIBLE
                text = context.getString(R.string.label_limit_max, value.toAmountText())
            }
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    internal fun ImageView.setImageUrl(imageUrl: String?) {
        imageUrl?.let {
            Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions()
                    .fitCenter())
                .into(this)
        }
    }
}