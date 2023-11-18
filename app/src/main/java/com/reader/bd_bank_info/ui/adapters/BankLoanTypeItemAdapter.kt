package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.databinding.RecyclerItemLoanTypeBinding
import com.reader.bd_bank_info.utils.DiffUtilCallback

class BankLoanTypeItemAdapter :
    RecyclerView.Adapter<BankLoanTypeItemAdapter.BankVerticalViewHolder>() {
    private val items = ArrayList<LoanType>()
    private var bankLoanTypeCallBack: BankLoanTypeCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankVerticalViewHolder {
        val binding = RecyclerItemLoanTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankVerticalViewHolder(binding, bankLoanTypeCallBack)
    }

    override fun onBindViewHolder(holder: BankVerticalViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(bankLoanTypeCallBack: BankLoanTypeCallBack) {
        this.bankLoanTypeCallBack = bankLoanTypeCallBack
    }

    fun addItems(navRails: List<LoanType>) {
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

    class BankVerticalViewHolder(
        val binding: RecyclerItemLoanTypeBinding,
        private val bankLoanTypeCallBack: BankLoanTypeCallBack?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(loanType: LoanType) {
            binding.titleView.text = loanType.loanTypeName
            loanType.loanTypeIcon?.let {
                binding.iconView.setImageResource(it)
            }

            binding.ibDetails.setOnClickListener {
                bankLoanTypeCallBack?.onLoanTypeClick(loanType)
            }

            itemView.setOnClickListener {
                bankLoanTypeCallBack?.onLoanTypeClick(loanType)
            }
        }
    }

    interface BankLoanTypeCallBack {
        fun onLoanTypeClick(loanType: LoanType)
    }
}