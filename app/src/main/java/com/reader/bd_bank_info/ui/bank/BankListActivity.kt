package com.reader.bd_bank_info.ui.bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.reader.bd_bank_info.databinding.ActivityBankListBinding

class BankListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankListBinding
    private lateinit var viewModel: BankViewModel

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
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewModel.onBankListFetched().observe(this){bankList ->
            bankList?.let {

            }
        }
    }
}