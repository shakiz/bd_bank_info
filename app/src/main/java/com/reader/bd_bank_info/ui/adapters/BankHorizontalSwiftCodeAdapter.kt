package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.RecyclerItemSwiftCodeHomeBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback

class BankHorizontalSwiftCodeAdapter : RecyclerView.Adapter<BankHorizontalSwiftCodeAdapter.BankSwiftCodeViewHolder>() {
    private val items = ArrayList<Bank>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankSwiftCodeViewHolder {
        val binding = RecyclerItemSwiftCodeHomeBinding.inflate(
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

    class BankSwiftCodeViewHolder(val binding: RecyclerItemSwiftCodeHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(bank: Bank) {
            binding.tvBankTitle.text = bank.bankName
            bank.bankIconRes?.let {
                binding.ivBankLogo.background = ContextCompat.getDrawable(itemView.context, it)
            }
        }
    }
}