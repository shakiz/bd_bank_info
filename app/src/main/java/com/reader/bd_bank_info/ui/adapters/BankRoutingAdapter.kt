package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Routings
import com.reader.bd_bank_info.databinding.RecyclerItemSwiftCodeBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback

class BankRoutingAdapter : RecyclerView.Adapter<BankRoutingAdapter.BankSwiftCodeViewHolder>() {
    private val items = ArrayList<Routings>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankSwiftCodeViewHolder {
        val binding = RecyclerItemSwiftCodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankSwiftCodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankSwiftCodeViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun addItems(navRails: List<Routings>) {
        val oldData = ArrayList(items)
        items.clear()
        items.addAll(navRails)

        val diffUtilCallback = DiffUtilCallback(
            oldData = oldData,
            newData = items,
            areItemsTheSame = { oldItem, newItem ->
                return@DiffUtilCallback oldItem == newItem
            },
            areContentsTheSame = { oldItem, newItem ->
                return@DiffUtilCallback oldItem == newItem
            }
        )
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        diffResult.dispatchUpdatesTo(this)
    }

    class BankSwiftCodeViewHolder(val binding: RecyclerItemSwiftCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(routings: Routings) {

        }
    }
}