package com.reader.bd_bank_info.ui.stockmarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.data.model.StockMarket
import com.reader.bd_bank_info.databinding.ActivityStockMarketListBinding
import com.reader.bd_bank_info.ui.adapters.StockMarketAdapter
import com.reader.bd_bank_info.ui.bank.BankViewModel
import com.reader.bd_bank_info.ui.commonwebview.CommonWebViewActivity
import com.reader.bd_bank_info.utils.STOCK_MARKET_BASE_URL
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.WEBVIEW_BUNDLE
import com.reader.bd_bank_info.utils.dimenSize

class StockMarketListActivity : AppCompatActivity(), StockMarketAdapter.StockMarketClickListener{

    private lateinit var binding: ActivityStockMarketListBinding
    private lateinit var viewModel: BankViewModel
    private val stockMarketAdapter = StockMarketAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockMarketListBinding.inflate(layoutInflater)
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
        viewModel.onBankListFetched().observe(this) {
            viewModel.fetchStockMarketList()
        }

        viewModel.onStockMarketFetched().observe(this) { stockMarketList ->
            stockMarketList?.let { stockMarketAdapter.addItems(it) }
        }
    }

    private fun setupBankListView() {
        binding.rvSwiftCodeList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSwiftCodeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvSwiftCodeList.adapter = stockMarketAdapter
        stockMarketAdapter.setItemClickListener(this)
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

    override fun onItemClick(stockMarket: StockMarket) {
        val bundle = CommonWebViewActivity.createIntent(this, "$STOCK_MARKET_BASE_URL${stockMarket.stockCode}", stockMarket.bankName)
        startActivity(Intent(this, CommonWebViewActivity::class.java).putExtra(WEBVIEW_BUNDLE, bundle))
    }
}