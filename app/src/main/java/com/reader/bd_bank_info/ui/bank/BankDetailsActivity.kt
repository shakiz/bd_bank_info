package com.reader.bd_bank_info.ui.bank

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivityBankDetailsBinding
import com.reader.bd_bank_info.utils.ITEM_BANK

class BankDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankDetailsBinding
    private lateinit var viewModel: BankViewModel
    private var bank: Bank? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bank =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(ITEM_BANK, Bank::class.java)
            } else {
                intent.getParcelableExtra(ITEM_BANK)
            }

        viewModel =
            ViewModelProvider(this)[BankViewModel::class.java]

        initView()
        initListeners()

        viewModel.fetchBankList()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = bank?.bankName
        setupBankDetailsUI()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupBankDetailsUI(){
        bank?.let {
            it.bankIconRes?.let { res ->
                binding.ivBankLogo.background = (ContextCompat.getDrawable(this, res))
            }
            binding.tvBankDetails.text = getString(R.string.bank_details_with_x_values, it.legalStatus, it.establishedDate, it.bankType, it.origin, it.corporateAddress)
            binding.tvHotlineNumber.text = getString(R.string.call_for_help_x, it.hotlineNo)
            binding.tvBankEmail.text = getString(R.string.send_mail_at_x, it.email)
            binding.tvBankCorporateAddress.text = getString(R.string.address_x, it.corporateAddress)
            binding.tvBankSwiftCode.text = getString(R.string.swift_code_x, it.swiftCode)
            binding.tvBankStockCode.text = getString(R.string.stock_code_x, it.stockCode)
            binding.tvBankOverseasNo.text = getString(R.string.for_overseas_x, it.hotlinePhoneNo)
        }
    }
}