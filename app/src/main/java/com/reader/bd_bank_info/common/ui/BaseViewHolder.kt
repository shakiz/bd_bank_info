package com.reader.bd_bank_info.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: Item)
}
