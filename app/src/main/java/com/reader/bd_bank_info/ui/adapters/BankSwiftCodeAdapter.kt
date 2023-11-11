package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.RecyclerItemSwiftCodeBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback
import com.reader.bd_bank_info.utils.isNull

class BankSwiftCodeAdapter : RecyclerView.Adapter<BankSwiftCodeAdapter.BankSwiftCodeViewHolder>() {
    private val items = ArrayList<Bank>()
    private var swiftCodeCallBack: SwiftCodeCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankSwiftCodeViewHolder {
        val binding = RecyclerItemSwiftCodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankSwiftCodeViewHolder(binding, swiftCodeCallBack)
    }

    override fun onBindViewHolder(holder: BankSwiftCodeViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setOnSwiftCodeCopyListener(swiftCodeCallBack: SwiftCodeCallBack){
        this.swiftCodeCallBack = swiftCodeCallBack
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

    class BankSwiftCodeViewHolder(val binding: RecyclerItemSwiftCodeBinding, private val swiftCodeCallBack: SwiftCodeCallBack?) :
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
            binding.root.setOnClickListener {
                swiftCodeCallBack?.onItemClicked(bank)
            }
            binding.ivCopy.setOnClickListener {
                swiftCodeCallBack?.onSwiftCodeCopied(bank)
            }

            binding.ivCall.setOnClickListener {
                swiftCodeCallBack?.onHotlineNumberCalled(bank)
            }
        }
    }

    interface SwiftCodeCallBack{
        fun onSwiftCodeCopied(bank: Bank)
        fun onHotlineNumberCalled(bank: Bank)

        fun onItemClicked(bank: Bank)
    }
}