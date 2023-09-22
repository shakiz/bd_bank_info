package com.example.androidplatter.common.pagination

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.common.LoaderState

abstract class PaginateAbleAdapter<Item> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VT_LOADER = 0
        const val VT_EMPTY = 1
        const val VT_ERROR = 2
        const val VT_ITEM = 9
    }

    var hasMoreData: Boolean = true
    private var hasError = false
    private var isEmpty = false

    protected val dataSet = ArrayList<Item>()
    private var loaderState: LoaderState? = null

    val dataSize: Int
        get() {
            return dataSet.size
        }

    override fun getItemCount(): Int {
        return when {
            isEmpty || hasError -> 1
            isLoadingData() -> dataSize + 1
            else -> dataSize
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isEmpty && position == 0 -> VT_EMPTY
            hasError && position == 0 -> VT_ERROR
            isLoadingData() && dataSet.isEmpty() && position == 0 -> VT_LOADER
            isLoadingData() && dataSet.isNotEmpty() && position == dataSize -> VT_LOADER
            else -> VT_ITEM
        }
    }

    fun isLoadingData() = loaderState != null && loaderState != LoaderState.LOADED

    abstract fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean

    open fun submitData(data: List<Item>) {
        hasMoreData = data.isNotEmpty()

        val oldData = ArrayList(dataSet)
        this.dataSet.addAll(data)
        isEmpty = dataSize == 0

        val diffUtilCallback = DiffUtilCallback(oldData, dataSet) { oldItem, newItem ->
            areItemsTheSame(oldItem, newItem)
        }
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    open fun resetData() {
        dataSet.clear()
        isEmpty = false
        hasError = false
        loaderState = null
        hasMoreData = true
    }

    fun setNetworkState(newState: LoaderState) {
        val previousState = this.loaderState
        val hadExtraRow = isLoadingData()
        this.loaderState = newState
        val hasExtraRow = isLoadingData()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun setErrorState() {
        dataSet.clear()
        hasError = true
        notifyDataSetChanged()
    }
}
