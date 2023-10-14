package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.StockMarket
import com.reader.bd_bank_info.databinding.RecyclerItemStockMarketBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback

class StockMarketAdapter : RecyclerView.Adapter<StockMarketAdapter.BankVerticalViewHolder>() {
    private val items = ArrayList<StockMarket>()
    private var stockMarketClickListener: StockMarketClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankVerticalViewHolder {
        val binding = RecyclerItemStockMarketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankVerticalViewHolder(binding).apply {
            itemView.setOnClickListener {
                stockMarketClickListener?.onItemClick(items[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: BankVerticalViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(stockMarketClickListener: StockMarketClickListener){
        this.stockMarketClickListener = stockMarketClickListener
    }

    fun addItems(navRails: List<StockMarket>) {
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

    class BankVerticalViewHolder(val binding: RecyclerItemStockMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(stockMarket: StockMarket) {
            binding.tvTitle.text = stockMarket.bankName
            binding.tvBankStockCode.text = binding.root.context.getString(R.string.stock_code_x, stockMarket.stockCode)
            stockMarket.bankIconRes?.let {
                binding.ivSlider.setImageResource(it)
            }
        }
    }

    interface StockMarketClickListener{
        fun onItemClick(stockMarket: StockMarket)
    }
}