package com.reader.bd_bank_info.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getDrawableOrThrow
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

fun Context.currentLanguage(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return resources.configuration.locales[0].language
    }

    return resources.configuration.locale.language
}

fun Context?.dimensionSize(@DimenRes dimenRes: Int): Int {
    return this?.resources?.getDimensionPixelSize(dimenRes) ?: 0
}

fun Context?.compatColor(@ColorRes colorRes: Int): Int {
    this ?: return Color.TRANSPARENT

    return ResourcesCompat.getColor(resources, colorRes, theme)
}

fun Context?.compatColorStateList(@ColorRes colorRes: Int): ColorStateList? {
    this ?: return null

    return ResourcesCompat.getColorStateList(resources, colorRes, theme)
}

fun Context?.compatDrawable(@DrawableRes drawableRes: Int): Drawable? {
    this ?: return null

    return ResourcesCompat.getDrawable(resources, drawableRes, theme)
}

fun Context?.selectableItemBgRes(): Int {
    if (this == null) return -1

    val outValue = TypedValue()
    theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    return outValue.resourceId
}

fun Context?.attrColor(@AttrRes attrRes: Int): Int {
    this ?: return Color.TRANSPARENT

    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context?.attrDrawable(@AttrRes drawableAttr: Int, styleRes: Int = 0): Drawable? {
    this ?: return null

    val value = TypedValue()
    if (styleRes != 0) {
        theme.resolveAttribute(styleRes, value, false)
    }

    val style = value.data
    val attributes = intArrayOf(drawableAttr)
    val array = obtainStyledAttributes(style, attributes)

    val drawable: Drawable? = try {
        array.getDrawableOrThrow(0)
    } catch (ex: Exception) {
        null
    }

    array.recycle()
    return drawable
}

fun Context?.showShortToast(@StringRes messageId: Int) {
    this?.showShortToast(getString(messageId))
}

fun Context?.showShortToast(message: String?) {
    this ?: return

    if (message.isNullOrBlank()) {
        return
    }
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context?.showLongToast(@StringRes messageId: Int) {
    this?.showLongToast(getString(messageId))
}

fun Context?.showLongToast(message: String?) {
    this ?: return

    if (message.isNullOrBlank()) {
        return
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun DialogFragment.setPercentileWidth(percentage: Float) {
    val percent = percentage / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = Rect(0, 0, dm.widthPixels, dm.heightPixels)
    val percentileWidth = rect.width() * percent

    dialog?.window?.setLayout(percentileWidth.toInt(), WRAP_CONTENT)
}

fun DialogFragment.makeFullScreenDialog() {
    dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
}

fun Activity?.hideSoftKeyboard() {
    this ?: return

    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(findViewById<View?>(android.R.id.content).windowToken, 0)
}

fun Fragment.hideSoftKeyboard() {
    activity.hideSoftKeyboard()
}

@FlowPreview
@ExperimentalCoroutinesApi
fun TextView.onTextChanged(debounceInterval: Long): Flow<CharSequence?> {
    return callbackFlow {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s)
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }.debounce(debounceInterval)
}