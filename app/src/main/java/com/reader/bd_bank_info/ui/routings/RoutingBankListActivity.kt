package com.reader.bd_bank_info.ui.routings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivityRoutingBankListBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.ui.bank.BankItemClickListener
import com.reader.bd_bank_info.utils.BANK_LIST_ITEM_VIEW_TYPE_ROUTING
import com.reader.bd_bank_info.utils.ITEM_BANK
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize

class RoutingBankListActivity : AppCompatActivity(), BankItemClickListener {

    private lateinit var binding: ActivityRoutingBankListBinding
    private lateinit var viewModel: RoutingViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutingBankListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[RoutingViewModel::class.java]

        initView()
        initListeners()
        initObservers()

        viewModel.fetchBankList()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        setupRoutingBankListView()
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

    private fun setupRoutingBankListView() {
        binding.rvBankList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvBankList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvBankList.adapter = bankItemAdapter
        bankItemAdapter.setViewType(BANK_LIST_ITEM_VIEW_TYPE_ROUTING)
        bankItemAdapter.setItemClickListener(this)
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

    override fun onItemClick(bank: Bank) {
        startActivity(Intent(this, RoutingDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
    }
}