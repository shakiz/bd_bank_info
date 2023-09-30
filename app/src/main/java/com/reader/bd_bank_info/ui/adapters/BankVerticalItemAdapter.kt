package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.RecyclerItemVerticalBankListBinding
import com.reader.bd_bank_info.ui.bank.BankItemClickListener
import com.reader.bd_bank_info.utils.*

class BankVerticalItemAdapter : RecyclerView.Adapter<BankVerticalItemAdapter.BankVerticalViewHolder>() {
    private val items = ArrayList<Bank>()
    private var bankItemClickListener: BankItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankVerticalViewHolder {
        val binding = RecyclerItemVerticalBankListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankVerticalViewHolder(binding).apply {
            itemView.setOnClickListener {
                bankItemClickListener?.onItemClick(items[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: BankVerticalViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(bankItemClickListener: BankItemClickListener){
        this.bankItemClickListener = bankItemClickListener
    }

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

    class BankVerticalViewHolder(val binding: RecyclerItemVerticalBankListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(bank: Bank) {
            binding.tvTitle.text = bank.bankName
            binding.tvType.text = bank.bankType
            binding.tvEstablishedAtAndCategory.text = itemView.context.getString(R.string.x_s_established_x_s, bank.bankCategory, bank.establishedDate.toString())
            bank.bankIconRes?.let {
                binding.ivSlider.background = ContextCompat.getDrawable(itemView.context, it)
            }
        }
    }
}