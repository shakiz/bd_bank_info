package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Routings
import com.reader.bd_bank_info.databinding.RecyclerItemRoutingDetailsBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback
import com.reader.bd_bank_info.utils.orZero

class RoutingItemAdapter : RecyclerView.Adapter<RoutingItemAdapter.BankViewHolder>() {
    private val items = ArrayList<Routings>()
    private var routingCopyClickListener: RoutingCopyClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val binding = RecyclerItemRoutingDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankViewHolder(binding, copyClickListener = routingCopyClickListener)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setCopyClickListener(routingCopyClickListener: RoutingCopyClickListener){
        this.routingCopyClickListener = routingCopyClickListener
    }

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

    class BankViewHolder(val binding: RecyclerItemRoutingDetailsBinding, val copyClickListener: RoutingCopyClickListener?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(routings: Routings) {
            val context = binding.root.context
            binding.tvIndex.text = context.getString(R.string.x_d, adapterPosition + 1)
            binding.tvDistricts.text = context.getString(R.string.district_name_x, routings.districts)
            binding.tvBranchName.text = context.getString(R.string.branch_name_x, routings.branchName)
            binding.tvRoutingNo.text = context.getString(R.string.routing_no_x, routings.routingNo)
            binding.cvCopyRoutingNo.setOnClickListener {
                copyClickListener?.onCopyClicked(routings.routingNo.orZero())
            }
        }
    }

    interface RoutingCopyClickListener{
        fun onCopyClicked(routingNo: Int)
    }
}