package com.example.androidplatter.common.pagination

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(
    private val oldData: List<T>,
    private val newData: List<T>,
    private val areItemsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null,
    private val areContentsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (areItemsTheSame == null) {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

        return areItemsTheSame.invoke(oldData[oldItemPosition], newData[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (areContentsTheSame == null) {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

        return areContentsTheSame.invoke(oldData[oldItemPosition], newData[newItemPosition])
    }
}