package com.reader.bd_bank_info.ui.loan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.databinding.ActivityBankLoanTypeListBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.ui.bank.BankViewModel
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize

class BankLoanTypeListActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBankLoanTypeListBinding
    private lateinit var viewModel: BankViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()
    private val analytics = AppInjector.getAnalytics(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankLoanTypeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[BankViewModel::class.java]

        initView()
        initListeners()
        initObservers()

        viewModel.fetchBankList()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        setupBankListView()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initObservers() {
        viewModel.onBankListFetched().observe(this) { bankList ->
            bankList?.let {
                bankItemAdapter.addItems(bankList)
            }
        }
    }

    private fun setupBankListView() {
        binding.rvBankLoanTypeList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvBankLoanTypeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvBankLoanTypeList.adapter = bankItemAdapter
    }
}