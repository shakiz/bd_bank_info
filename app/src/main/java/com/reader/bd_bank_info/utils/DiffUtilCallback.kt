package com.reader.bd_bank_info.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(
    private val oldData: List<T>,
    private val newData: List<T>,
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame.invoke(oldData[oldItemPosition], newData[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame?.invoke(oldData[oldItemPosition], newData[newItemPosition])
                ?: (oldData[oldItemPosition] == newData[newItemPosition])
    }
}
