package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.RecyclerItemHorizontalBankListBinding
import com.reader.bd_bank_info.ui.bank.BankItemClickListener
import com.reader.bd_bank_info.utils.*

class BankItemAdapter : RecyclerView.Adapter<BankItemAdapter.BankViewHolder>() {
    private val items = ArrayList<Bank>()
    private var bankItemClickListener: BankItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val binding = RecyclerItemHorizontalBankListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankViewHolder(binding).apply {
            itemView.setOnClickListener {
                bankItemClickListener?.onItemClick(items[adapterPosition])
            }
        }
    }

    fun setOnItemClickListener(bankItemClickListener: BankItemClickListener){
        this.bankItemClickListener = bankItemClickListener
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun addItems(navRails: List<Bank>) {
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

    class BankViewHolder(val binding: RecyclerItemHorizontalBankListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(bank: Bank) {
            binding.tvTitle.text = bank.bankName
            binding.tvSubTitle.text = bank.bankType
            bank.bankIconRes?.let {
                binding.ivSlider.setImageResource(it)
            }
        }
    }
}