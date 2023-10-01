package com.reader.bd_bank_info.ui.bank

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
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
            binding.tvBankTitle.text = it.bankName
            binding.tvBankLegalStatus.text = it.legalStatus
            binding.tvBankType.text = it.bankType
            binding.tvBankCategory.text = it.bankCategory
            binding.tvBankOrigin.text = it.origin
            binding.tvBankCorporateAddress.text = it.corporateAddress
            binding.tvHotlineNumber.text = it.hotlineNo.toString()
            binding.tvOverseas.text = it.hotlinePhoneNo
            binding.tvBankWebsite.text = it.address
            binding.tvBankSwiftCode.text = it.swiftCode
            binding.tvBankStockCode.text = it.stockCode
        }
    }
}