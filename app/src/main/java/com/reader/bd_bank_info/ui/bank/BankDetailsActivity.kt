package com.reader.bd_bank_info.ui.bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.reader.bd_bank_info.databinding.ActivityBankDetailsBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter

class BankDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankDetailsBinding
    private lateinit var viewModel: BankViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankDetailsBinding.inflate(layoutInflater)
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
}