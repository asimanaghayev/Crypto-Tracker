package com.asiman.module_network.extensions

fun List<String>.toQueryString(): String {
    var result = "";
    for (value in this) {
        result = if (result.isEmpty()) {
            result.plus(value)
        } else {
            result.plus(",$value")
        }
    }
    return result
}