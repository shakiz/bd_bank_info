package com.reader.bd_bank_info.ui.bank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reader.bd_bank_info.databinding.ActivityBankListBinding

class BankListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }
}