package com.example.module_ui_kit.support.extensions

import com.example.module_utility.round
import java.math.BigDecimal


fun BigDecimal.toAmountText(): String {
    return ("$").plus(this.round().toString())
}