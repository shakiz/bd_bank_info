package com.reader.bd_bank_info.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.databinding.ActivityMainBinding
import com.reader.bd_bank_info.ui.adapters.BankItemAdapter
import com.reader.bd_bank_info.ui.adapters.NavRailAdapter
import com.reader.bd_bank_info.ui.bank.BankDetailsActivity
import com.reader.bd_bank_info.ui.bank.BankItemClickListener
import com.reader.bd_bank_info.ui.bank.BankListActivity
import com.reader.bd_bank_info.ui.swiftcode.SwiftCodeListActivity
import com.reader.bd_bank_info.utils.*

class HomeActivity : AppCompatActivity(), BankItemClickListener, NavRailAdapter.NavRailClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel

    private val navRailAdapter = NavRailAdapter()
    private val bankItemAdapter = BankItemAdapter()

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
    }

    private fun initListeners(){
        binding.layoutBankList.tvSeeAll.setOnClickListener {
            startActivity(Intent(this, BankListActivity::class.java))
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
        binding.layoutBankList.rvBankList.layoutManager = PartiallyVisibleHorizontalLayoutManager(this, .4f)
        binding.layoutBankList.rvBankList.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp)))
        binding.layoutBankList.rvBankList.adapter = bankItemAdapter
        bankItemAdapter.setOnItemClickListener(this)
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
        }
    }
}