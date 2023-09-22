package com.reader.bd_bank_info.common.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PartiallyVisibleHorizontalLayoutManager(
    context: Context?,
    private val minWidthPercentage: Float = 1f
) : LinearLayoutManager(context, HORIZONTAL, false) {

    var itemWidth = 0
        private set

    fun remainingOffset() = width - itemWidth

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        if (itemCount > 1 && minWidthPercentage != 1f && lp?.width != itemWidth) {
            itemWidth = (width * minWidthPercentage).toInt()
            lp?.width = itemWidth
        } else if (minWidthPercentage == 1f) {
            itemWidth = width
        }
        return super.checkLayoutParams(lp)
    }
}
