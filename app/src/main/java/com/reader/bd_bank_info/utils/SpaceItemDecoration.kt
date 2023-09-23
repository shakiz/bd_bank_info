package com.reader.bd_bank_info.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class SpaceItemDecoration(
    private val space: Int,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val childPosition = parent.getChildLayoutPosition(view)
        val childCount = parent.adapter?.itemCount.orZero()

        parent.layoutManager?.let { layoutManager ->
            when (layoutManager) {
                is GridLayoutManager -> {
                    val spanCount = layoutManager.spanCount
                    setGridItemSpace(outRect, childPosition, spanCount, childCount)
                }
                is LinearLayoutManager -> {
                    when (layoutManager.orientation) {
                        RecyclerView.VERTICAL -> setVerticalSpace(outRect, childPosition, childCount)
                        else -> setHorizontalSpace(outRect, childPosition, childCount)
                    }
                }
            }
        }
    }

    private fun setVerticalSpace(outRect: Rect, position: Int, childCount: Int) {
        outRect.top = if (position == 0 && !includeEdge) 0 else if (position == 0 && includeEdge) space else space / 2
        outRect.bottom = if (position == childCount - 1 && !includeEdge) 0 else if (position == childCount - 1 && includeEdge) space else space / 2
        outRect.left = if (includeEdge) space else 0
        outRect.right = if (includeEdge) space else 0
    }

    protected open fun setHorizontalSpace(outRect: Rect, position: Int, childCount: Int) {
        outRect.left = if (position == 0 && !includeEdge) 0 else if (position == 0 && includeEdge) space else space / 2
        outRect.right = if (position == childCount - 1 && !includeEdge) 0 else if (position == childCount - 1 && includeEdge) space else space / 2
        outRect.top = if (includeEdge) space else 0
        outRect.bottom = if (includeEdge) space else 0
    }

    private fun setGridItemSpace(outRect: Rect, position: Int, spanCount: Int, childCount: Int) {
        outRect.left = if (position % spanCount != 0) space / 2 else if (includeEdge) space else 0
        outRect.top = if (position >= spanCount) space / 2 else if (includeEdge) space else 0
        outRect.right = if (position % spanCount != spanCount - 1) space / 2 else if (includeEdge) space else 0
        outRect.bottom = if (position <= (childCount / spanCount) * spanCount) space / 2 else if (includeEdge) space else 0
    }
}

class HorizontalSpaceItemDecoration(
    private val mainAxisSpace: Int = 0,
    private val crossAxisSpace: Int = 0,
    private val includeEdge: Boolean = true
) : SpaceItemDecoration(mainAxisSpace, includeEdge) {

    override fun setHorizontalSpace(outRect: Rect, position: Int, childCount: Int) {
        outRect.left = if (position == 0 && !includeEdge) 0 else if (position == 0) mainAxisSpace else mainAxisSpace / 2
        outRect.right = if (position == childCount - 1 && !includeEdge) 0 else if (position == childCount - 1) mainAxisSpace else mainAxisSpace / 2
        outRect.top = crossAxisSpace
        outRect.bottom = crossAxisSpace
    }
}
