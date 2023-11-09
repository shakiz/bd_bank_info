package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.RecyclerItemSwiftCodeBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback
import com.reader.bd_bank_info.utils.isNull
import com.reader.bd_bank_info.utils.orZero

class BankSwiftCodeAdapter : RecyclerView.Adapter<BankSwiftCodeAdapter.BankSwiftCodeViewHolder>() {
    private val items = ArrayList<Bank>()
    private var swiftCodeCopyListener: SwiftCodeCopyListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankSwiftCodeViewHolder {
        val binding = RecyclerItemSwiftCodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankSwiftCodeViewHolder(binding, swiftCodeCopyListener)
    }

    override fun onBindViewHolder(holder: BankSwiftCodeViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setOnSwiftCodeCopyListener(swiftCodeCopyListener: SwiftCodeCopyListener){
        this.swiftCodeCopyListener = swiftCodeCopyListener
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

    class BankSwiftCodeViewHolder(val binding: RecyclerItemSwiftCodeBinding, private val swiftCodeCopyListener: SwiftCodeCopyListener?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(bank: Bank) {
            binding.tvBankTitle.text = bank.bankName
            binding.tvBankSwiftCode.text = bank.swiftCode
            binding.tvHotlineNumber.text = if(!bank.hotlineNo.isNull()){
                bank.hotlineNo.toString()
            } else {
                "N/A"
            }
            bank.bankIconRes?.let {
                binding.ivSlider.setImageResource(it)
            }
            binding.ivCopy.setOnClickListener {
                swiftCodeCopyListener?.onSwiftCodeCopied(bank)
            }

            binding.ivCall.setOnClickListener {
                swiftCodeCopyListener?.onHotlineNumberCalled(bank)
            }
        }
    }

    interface SwiftCodeCopyListener{
        fun onSwiftCodeCopied(bank: Bank)
        fun onHotlineNumberCalled(bank: Bank)
    }
}