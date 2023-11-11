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
    private var swiftCodeItemClickListener: SwiftCodeItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankSwiftCodeViewHolder {
        val binding = RecyclerItemSwiftCodeHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankSwiftCodeViewHolder(binding, swiftCodeItemClickListener)
    }

    override fun onBindViewHolder(holder: BankSwiftCodeViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    fun setItemClickListener(swiftCodeItemClickListener: SwiftCodeItemClickListener?){
        this.swiftCodeItemClickListener = swiftCodeItemClickListener
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

    class BankSwiftCodeViewHolder(val binding: RecyclerItemSwiftCodeHomeBinding, val  swiftCodeItemClickListener: SwiftCodeItemClickListener?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(bank: Bank) {
            binding.tvBankTitle.text = bank.bankName
            bank.bankIconRes?.let {
                binding.ivBankLogo.setImageResource(it)
            }

            binding.root.setOnClickListener {
                swiftCodeItemClickListener?.onSwiftCodeItemClick(bank)
            }
        }
    }

    interface SwiftCodeItemClickListener{
        fun onSwiftCodeItemClick(bank: Bank)
    }
}