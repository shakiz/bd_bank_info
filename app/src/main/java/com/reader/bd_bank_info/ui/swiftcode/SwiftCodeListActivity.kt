package com.reader.bd_bank_info.ui.swiftcode

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivitySwiftCodeListBinding
import com.reader.bd_bank_info.ui.adapters.BankSwiftCodeAdapter
import com.reader.bd_bank_info.ui.bank.BankViewModel
import com.reader.bd_bank_info.utils.BANK_HOTLINE_NO_TAPPED
import com.reader.bd_bank_info.utils.BANK_NAME
import com.reader.bd_bank_info.utils.BANK_SEARCH_TAPPED
import com.reader.bd_bank_info.utils.BANK_SWIFT_CODE_COPIED
import com.reader.bd_bank_info.utils.REQUEST_CALL_CODE
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize
import com.reader.bd_bank_info.utils.hideSoftKeyboard
import com.reader.bd_bank_info.utils.orZero
import com.reader.bd_bank_info.utils.showLongToast

class SwiftCodeListActivity : AppCompatActivity(), BankSwiftCodeAdapter.SwiftCodeCallBack {

    private lateinit var binding: ActivitySwiftCodeListBinding
    private lateinit var viewModel: BankViewModel
    private val appAnalytics = AppInjector.getAnalytics(this)

    private val swiftCodeAdapter = BankSwiftCodeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwiftCodeListBinding.inflate(layoutInflater)
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

        binding.searchLayout.ibSearchButton.setOnClickListener {
            appAnalytics.registerEvent(BANK_SEARCH_TAPPED, appAnalytics.setData(BANK_NAME, binding.searchLayout.etSearchName.text.toString()))
            hideSoftKeyboard()
            viewModel.searchBankItem(binding.searchLayout.etSearchName.text.toString())
        }

        binding.searchLayout.ibRefreshButton.setOnClickListener {
            hideSoftKeyboard()
            binding.searchLayout.etSearchName.text?.clear()
            viewModel.fetchBankList()
        }
    }

    private fun initObservers() {
        viewModel.onBankListFetched().observe(this) { bankList ->
            bankList?.let {
                swiftCodeAdapter.addItems(bankList)
            }
        }
    }

    private fun setupBankListView() {
        binding.rvSwiftCodeList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSwiftCodeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvSwiftCodeList.adapter = swiftCodeAdapter
        swiftCodeAdapter.setOnSwiftCodeCopyListener(this)
    }

    override fun onSwiftCodeCopied(bank: Bank) {
        appAnalytics.registerEvent(BANK_SWIFT_CODE_COPIED, appAnalytics.setData(BANK_NAME, bank.bankName))
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(bank.swiftCode, bank.swiftCode)
        clipboard.setPrimaryClip(clip)
        showLongToast(getString(com.reader.bd_bank_info.R.string.swift_code_copied))
    }

    override fun onHotlineNumberCalled(bank: Bank) {
        appAnalytics.registerEvent(BANK_HOTLINE_NO_TAPPED, appAnalytics.setData(BANK_NAME, bank.bankName))
        val hotlineNo = bank.hotlineNo.orZero()
        if(hotlineNo <= 0){
            return
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val uri = "tel:$hotlineNo"
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(uri)
            showLongToast(getString(com.reader.bd_bank_info.R.string.calling_x_hotline_number, hotlineNo))
            startActivity(callIntent)
        } else {
            Toast.makeText(
                this,
                getString(com.reader.bd_bank_info.R.string.please_allow_call_permission),
                Toast.LENGTH_SHORT
            ).show()
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_CODE
                )
            }
        }
    }

    override fun onItemClicked(bank: Bank) {
        appAnalytics.registerEvent(BANK_SWIFT_CODE_COPIED, appAnalytics.setData(BANK_NAME, bank.bankName))
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(bank.swiftCode, bank.swiftCode)
        clipboard.setPrimaryClip(clip)
        showLongToast(getString(com.reader.bd_bank_info.R.string.swift_code_copied))
    }
}