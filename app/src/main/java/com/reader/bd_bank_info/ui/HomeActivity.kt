package com.reader.bd_bank_info.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.databinding.ActivityMainBinding
import com.reader.bd_bank_info.ui.adapters.BankHorizontalSwiftCodeAdapter
import com.reader.bd_bank_info.ui.adapters.BankItemAdapter
import com.reader.bd_bank_info.ui.adapters.NavRailAdapter
import com.reader.bd_bank_info.ui.bank.BankDetailsActivity
import com.reader.bd_bank_info.ui.bank.BankItemClickListener
import com.reader.bd_bank_info.ui.bank.BankListActivity
import com.reader.bd_bank_info.ui.commonwebview.CommonWebViewActivity
import com.reader.bd_bank_info.ui.routings.RoutingBankListActivity
import com.reader.bd_bank_info.ui.swiftcode.SwiftCodeListActivity
import com.reader.bd_bank_info.utils.*

class HomeActivity : AppCompatActivity(), BankItemClickListener, NavRailAdapter.NavRailClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel

    private val navRailAdapter = NavRailAdapter()
    private val bankItemAdapter = BankItemAdapter()
    private val swiftCodeAdapter = BankHorizontalSwiftCodeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        initView()
        initListeners()
        initObservers()

        viewModel.fetchNavigationRailItems()
        viewModel.fetchBankList()
    }

    private fun initView(){
        setUpNavRailListView()
        setUpBankListView()
        setupSwiftCodeListView()
    }

    private fun initListeners(){
        binding.tvSeeAllBank.setOnClickListener {
            startActivity(Intent(this, BankListActivity::class.java))
        }

        binding.tvSeeAllSwiftCode.setOnClickListener {
            startActivity(Intent(this, SwiftCodeListActivity::class.java))
        }
    }

    private fun initObservers(){
        viewModel.onNavigationRailFetched().observe(this){ railItems ->
            railItems?.let {
                navRailAdapter.addItems(it)
            }
        }

        viewModel.onBankListFetched().observe(this){bankList ->
            bankList?.let {
                bankItemAdapter.addItems(it)
                swiftCodeAdapter.addItems(it.takeLast(3))
            }
        }
    }

    private fun setUpNavRailListView(){
        binding.rvNavRail.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvNavRail.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp)))
        binding.rvNavRail.adapter = navRailAdapter
        navRailAdapter.setItemClickListener(this)
    }

    private fun setUpBankListView(){
        binding.rvBankList.layoutManager = PartiallyVisibleHorizontalLayoutManager(this, .3f)
        binding.rvBankList.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp)))
        binding.rvBankList.adapter = bankItemAdapter
        bankItemAdapter.setOnItemClickListener(this)
    }

    private fun setupSwiftCodeListView(){
        binding.rvSwiftCodeList.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL,false)
        binding.rvSwiftCodeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp), false))
        binding.rvSwiftCodeList.adapter = swiftCodeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(bank: Bank) {
        startActivity(Intent(this, BankDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
    }

    override fun onItemClick(navRail: NavigationRail) {
        when(navRail.identifier){
            IDENTIFIER_SWIFT_CODE -> startActivity(Intent(this, SwiftCodeListActivity::class.java))
            IDENTIFIER_BANK -> startActivity(Intent(this, BankListActivity::class.java))
            IDENTIFIER_CURRENCY_RATES ->{
                val bundle = CommonWebViewActivity.createIntent(this, navRail.content?.url, getString(R.string.currency_rates))
                startActivity(Intent(this, CommonWebViewActivity::class.java).putExtra(WEBVIEW_BUNDLE, bundle))
            }
            IDENTIFIER_ROUTING_NO -> startActivity(Intent(this, RoutingBankListActivity::class.java))
        }
    }
}