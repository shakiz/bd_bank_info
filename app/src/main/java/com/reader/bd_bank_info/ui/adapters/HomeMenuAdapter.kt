package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.MainMenuItem
import com.reader.bd_bank_info.databinding.RecyclerItemHomeMenuBinding
import com.reader.bd_bank_info.utils.*

class HomeMenuAdapter : RecyclerView.Adapter<HomeMenuAdapter.NavigationRailViewHolder>() {

    private val items = ArrayList<MainMenuItem>()
    private var navRailClickListener: NavRailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationRailViewHolder {
        val binding = RecyclerItemHomeMenuBinding.inflate(
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

    fun addItems(navRails: List<MainMenuItem>) {
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

    class NavigationRailViewHolder(val binding: RecyclerItemHomeMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(navRail: MainMenuItem) {
            binding.tvTitle.text = navRail.titleEn
            navRail.icon?.let { binding.ivIcon.setImageResource(it) }
        }
    }

    interface NavRailClickListener{
        fun onItemClick(navRail: MainMenuItem)
    }
}
