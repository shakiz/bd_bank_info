package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.databinding.RecyclerItemLoanTypeBinding
import com.reader.bd_bank_info.ui.loan.LoanCallBack
import com.reader.bd_bank_info.utils.DiffUtilCallback

class BankLoanTypeItemAdapter :
    RecyclerView.Adapter<BankLoanTypeItemAdapter.BankVerticalViewHolder>() {
    private val items = ArrayList<LoanType>()
    private var loanCallBack: LoanCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankVerticalViewHolder {
        val binding = RecyclerItemLoanTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankVerticalViewHolder(binding, loanCallBack)
    }

    override fun onBindViewHolder(holder: BankVerticalViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(loanCallBack: LoanCallBack) {
        this.loanCallBack = loanCallBack
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
        private val loanCallBack: LoanCallBack?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(loanType: LoanType) {
            binding.titleView.text = loanType.loanTypeName
            loanType.loanTypeIcon?.let {
                binding.iconView.setImageResource(it)
            }

            binding.ibDetails.setOnClickListener {
                loanCallBack?.onLoanTypeClick(loanType)
            }

            itemView.setOnClickListener {
                loanCallBack?.onLoanTypeClick(loanType)
            }
        }
    }
}