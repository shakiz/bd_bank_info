package com.reader.bd_bank_info.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0

fun Float?.orZero(): Float = this ?: 0F

fun Double?.orZero(): Double = this ?: 0.0

fun String?.or(text: String): String = this ?: text

fun String?.equalsIgnoreCase(other: String?): Boolean {
    return equals(other, true)
}

fun String?.containsIgnoreCase(other: String?): Boolean {
    if (this == null || other == null) return false

    return contains(other, true)
}

fun <T> T.toMap(): Map<String, Any> {
    return convert()
}

inline fun <reified T> Map<String, Any?>.toObject(): T {
    return convert()
}

inline fun <T, reified R> T.convert(): R {
    val json = Gson().toJson(this)
    return Gson().fromJson(json, object : TypeToken<R>() {}.type)
}