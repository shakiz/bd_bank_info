package com.reader.bd_bank_info.ui.bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.databinding.ActivityBankListBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize

class BankListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankListBinding
    private lateinit var viewModel: BankViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankListBinding.inflate(layoutInflater)
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
        binding.rvBankList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvBankList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvBankList.adapter = bankItemAdapter
    }
}