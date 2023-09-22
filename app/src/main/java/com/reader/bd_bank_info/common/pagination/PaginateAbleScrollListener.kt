package com.example.androidplatter.common.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginateAbleScrollListener<D>(
    private val layoutManager: LinearLayoutManager,
    private val adapter: PaginateAbleAdapter<D>,
    private val prefetchCount: Int = 0
) : RecyclerView.OnScrollListener() {

    abstract fun loadMoreData(offset: Int)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (adapter.isLoadingData() || !adapter.hasMoreData) {
            return
        }

        if (dy > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - prefetchCount)) {
                loadMoreData(adapter.dataSize)
            }
        }
    }
}
