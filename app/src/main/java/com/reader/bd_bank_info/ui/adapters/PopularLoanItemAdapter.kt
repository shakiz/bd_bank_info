package com.reader.bd_bank_info.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.data.model.loan.PopularLoan
import com.reader.bd_bank_info.databinding.RecyclerItemPopularLoanBinding
import com.reader.bd_bank_info.ui.loan.LoanCallBack
import com.reader.bd_bank_info.utils.DiffUtilCallback

class PopularLoanItemAdapter :
    RecyclerView.Adapter<PopularLoanItemAdapter.BankVerticalViewHolder>() {
    private val items = ArrayList<PopularLoan>()
    private var loanCallback: LoanCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankVerticalViewHolder {
        val binding = RecyclerItemPopularLoanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BankVerticalViewHolder(binding, loanCallback)
    }

    override fun onBindViewHolder(holder: BankVerticalViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemClickListener(bankLoanTypeCallBack: LoanCallBack) {
        this.loanCallback = bankLoanTypeCallBack
    }

    fun addItems(navRails: List<PopularLoan>) {
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
        val binding: RecyclerItemPopularLoanBinding,
        private val loanCallback: LoanCallBack?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(popularLoan: PopularLoan) {
            binding.titleView.text = popularLoan.loanName
            //binding.tvTitle.text = popularLoan.bankName?.get(0).toString().uppercase()
            itemView.setOnClickListener {
                loanCallback?.onPopularLoanClick(popularLoan)
            }
        }
    }
}