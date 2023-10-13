package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.databinding.RecyclerItemNavigationRailBinding
import com.reader.bd_bank_info.utils.*

class NavRailAdapter : RecyclerView.Adapter<NavRailAdapter.NavigationRailViewHolder>() {

    private val items = ArrayList<NavigationRail>()
    private var navRailClickListener: NavRailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationRailViewHolder {
        val binding = RecyclerItemNavigationRailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationRailViewHolder(binding).apply {
            itemView.setOnClickListener {
                navRailClickListener?.onItemClick(items[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: NavigationRailViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(navRailClickListener: NavRailClickListener){
        this.navRailClickListener = navRailClickListener
    }

    fun addItems(navRails: List<NavigationRail>) {
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

    class NavigationRailViewHolder(val binding: RecyclerItemNavigationRailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(navRail: NavigationRail) {
            val context = binding.cvRoot.context

            binding.cvRoot.strokeColor = context.attrColor(androidx.appcompat.R.attr.colorPrimary)
            if (adapterPosition == 0) {
                binding.tvTitle.setBackgroundColor(context.compatColor(R.color.colorPrimary))
                binding.tvTitle.setTextColor(context.compatColor(R.color.colorOnPrimary))
            } else {
                binding.tvTitle.setBackgroundColor(context.compatColor(R.color.window_background2))
                binding.tvTitle.setTextColor(context.compatColor(R.color.colorOnSecondary))
            }

            binding.tvTitle.text = navRail.titleEn
        }
    }

    interface NavRailClickListener{
        fun onItemClick(navRail: NavigationRail)
    }
}
