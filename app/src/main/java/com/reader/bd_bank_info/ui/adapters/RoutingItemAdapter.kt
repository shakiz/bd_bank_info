package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Routings
import com.reader.bd_bank_info.databinding.RecyclerItemRoutingDetailsBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback

class RoutingItemAdapter : RecyclerView.Adapter<RoutingItemAdapter.BankViewHolder>() {
    private val items = ArrayList<Routings>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val binding = RecyclerItemRoutingDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
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

    class BankViewHolder(val binding: RecyclerItemRoutingDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(routings: Routings) {
            binding.tvDistricts.text = routings.districts
            binding.tvBranchName.text = routings.branchName
            binding.tvRoutingNo.text = routings.routingNo.toString()
        }
    }
}