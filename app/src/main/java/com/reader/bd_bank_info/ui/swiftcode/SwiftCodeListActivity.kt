package com.reader.bd_bank_info.ui.swiftcode

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.databinding.ActivitySwiftCodeListBinding
import com.reader.bd_bank_info.ui.adapters.BankSwiftCodeAdapter
import com.reader.bd_bank_info.ui.bank.BankViewModel
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.intuit.sdp.R
import com.reader.bd_bank_info.utils.dimenSize

class SwiftCodeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySwiftCodeListBinding
    private lateinit var viewModel: BankViewModel
    private val bankItemAdapter = BankSwiftCodeAdapter()

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
            closeKeyboard()
            viewModel.searchBankItem(binding.searchLayout.etSearchName.text.toString())
        }

        binding.searchLayout.ibRefreshButton.setOnClickListener {
            closeKeyboard()
            binding.searchLayout.etSearchName.text?.clear()
            viewModel.fetchBankList()
        }
    }

    private fun initObservers() {
        viewModel.onBankListFetched().observe(this) { bankList ->
            bankList?.let {
                bankItemAdapter.addItems(bankList)
            }
        }
    }

    private fun setupBankListView() {
        binding.rvSwiftCodeList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSwiftCodeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvSwiftCodeList.adapter = bankItemAdapter
    }

    private fun closeKeyboard() {
        currentFocus?.let {
            val manager: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    it.windowToken, 0
                )
        }
    }
}