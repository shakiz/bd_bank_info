package com.reader.bd_bank_info.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.MainMenuItem
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.data.model.loan.PopularLoan
import com.reader.bd_bank_info.databinding.ActivityMainBinding
import com.reader.bd_bank_info.ui.adapters.BankHorizontalSwiftCodeAdapter
import com.reader.bd_bank_info.ui.adapters.BankItemAdapter
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.ui.adapters.HomeMenuAdapter
import com.reader.bd_bank_info.ui.adapters.PopularLoanItemAdapter
import com.reader.bd_bank_info.ui.bank.BankDetailsActivity
import com.reader.bd_bank_info.ui.bank.BankCallBack
import com.reader.bd_bank_info.ui.bank.BankListActivity
import com.reader.bd_bank_info.ui.commonwebview.CommonWebViewActivity
import com.reader.bd_bank_info.ui.loan.BankLoanTypeListActivity
import com.reader.bd_bank_info.ui.loan.LoanCallBack
import com.reader.bd_bank_info.ui.routings.RoutingBankListActivity
import com.reader.bd_bank_info.ui.routings.RoutingDetailsActivity
import com.reader.bd_bank_info.ui.stockmarket.StockMarketListActivity
import com.reader.bd_bank_info.ui.swiftcode.SwiftCodeListActivity
import com.reader.bd_bank_info.utils.*

class HomeActivity : AppCompatActivity(), BankCallBack, HomeMenuAdapter.NavRailClickListener,
    BankHorizontalSwiftCodeAdapter.SwiftCodeItemClickListener, LoanCallBack {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private val appAnalytics = AppInjector.getAnalytics(this)

    private val homeMenuAdapter = HomeMenuAdapter()
    private val bankItemAdapter = BankItemAdapter()
    private val bankVerticalItemAdapter = BankVerticalItemAdapter()
    private val swiftCodeAdapter = BankHorizontalSwiftCodeAdapter()
    private val popularLoanItemAdapter = PopularLoanItemAdapter()

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
        viewModel.fetchPopularLoanList()
    }

    private fun initView() {
        setUpNavRailListView()
        setUpBankListView()
        setupSwiftCodeListView()
        setupRoutingListView()
        setUpPopularLoanListView()
    }

    private fun initListeners() {
        binding.tvSeeAllBank.setOnClickListener {
            appAnalytics.registerEvent(
                SEE_ALL_TAPPED,
                appAnalytics.setData(SEE_ALL_TAPPED, SEE_ALL_BANK)
            )
            startActivity(Intent(this, BankListActivity::class.java))
        }

        binding.tvSeeAllSwiftCode.setOnClickListener {
            appAnalytics.registerEvent(
                SEE_ALL_TAPPED,
                appAnalytics.setData(SEE_ALL_TAPPED, SEE_ALL_SWIFT_CODE)
            )
            startActivity(Intent(this, SwiftCodeListActivity::class.java))
        }

        binding.tvSeeAllBankRouting.setOnClickListener {
            appAnalytics.registerEvent(
                SEE_ALL_TAPPED,
                appAnalytics.setData(SEE_ALL_TAPPED, SEE_ALL_BANK_ROUTING)
            )
            startActivity(Intent(this, RoutingBankListActivity::class.java))
        }

        binding.layoutPopularLoan.tvSeeAllPopularLoans.setOnClickListener {

        }
    }

    private fun initObservers() {
        viewModel.onNavigationRailFetched().observe(this) { railItems ->
            railItems?.let {
                homeMenuAdapter.addItems(it)
            }
        }

        viewModel.onBankListFetched().observe(this) { bankList ->
            bankList?.let {
                bankItemAdapter.addItems(it)
                bankVerticalItemAdapter.addItems(it)
                swiftCodeAdapter.addItems(it.takeLast(3))
            }
        }

        viewModel.onPopularLoanListFetched().observe(this){ popularLoans ->
            popularLoans?.let {
                popularLoanItemAdapter.addItems(it)
            }
        }
    }

    private fun setUpNavRailListView() {
        binding.rvNavRail.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvNavRail.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp)))
        binding.rvNavRail.adapter = homeMenuAdapter
        homeMenuAdapter.setItemClickListener(this)
    }

    private fun setUpBankListView() {
        binding.rvBankList.layoutManager = PartiallyVisibleHorizontalLayoutManager(this, .3f)
        binding.rvBankList.addItemDecoration(
            SpaceItemDecoration(
                this.dimenSize(com.intuit.sdp.R.dimen._8sdp),
                false
            )
        )
        binding.rvBankList.adapter = bankItemAdapter
        bankItemAdapter.setOnItemClickListener(this)
    }

    private fun setupSwiftCodeListView() {
        binding.rvSwiftCodeList.layoutManager =
            GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        binding.rvSwiftCodeList.addItemDecoration(
            SpaceItemDecoration(
                this.dimenSize(com.intuit.sdp.R.dimen._8sdp),
                false
            )
        )
        binding.rvSwiftCodeList.adapter = swiftCodeAdapter
        swiftCodeAdapter.setItemClickListener(this)
    }

    private fun setupRoutingListView() {
        binding.rvBankListRouting.layoutManager =
            PartiallyVisibleHorizontalLayoutManager(this, .45f)
        binding.rvBankListRouting.addItemDecoration(
            SpaceItemDecoration(
                this.dimenSize(com.intuit.sdp.R.dimen._8sdp),
                false
            )
        )
        binding.rvBankListRouting.adapter = bankVerticalItemAdapter
        bankVerticalItemAdapter.setItemClickListener(this)
        bankVerticalItemAdapter.setViewType(BANK_LIST_ITEM_VIEW_TYPE_ROUTING)
    }

    private fun setUpPopularLoanListView() {
        binding.layoutPopularLoan.rvPopularLoanList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.layoutPopularLoan.rvPopularLoanList.addItemDecoration(SpaceItemDecoration(this.dimenSize(com.intuit.sdp.R.dimen._8sdp), false))
        binding.layoutPopularLoan.rvPopularLoanList.adapter = popularLoanItemAdapter
        popularLoanItemAdapter.setItemClickListener(this)
    }

    override fun onItemClick(bank: Bank) {
        appAnalytics.registerEvent(
            BANK_DETAILS_TAPPED,
            appAnalytics.setData(BANK_NAME, bank.bankName)
        )
        startActivity(Intent(this, BankDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
    }

    override fun onMailClicked(bank: Bank) {
        // empty implementation
    }

    override fun onHotlineNumberCalled(bank: Bank) {
        // empty implementation
    }

    override fun onRoutingItemClick(bank: Bank) {
        appAnalytics.registerEvent(
            BANK_ROUTING_TAPPED,
            appAnalytics.setData(BANK_ROUTING_TAPPED, bank.bankName)
        )
        startActivity(Intent(this, RoutingDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
    }

    override fun onItemClick(navRail: MainMenuItem) {
        appAnalytics.registerEvent(
            APP_MENU_ITEM_TAPPED,
            appAnalytics.setData(APP_MENU_ITEM_TAPPED, navRail.identifier)
        )
        when (navRail.identifier) {
            IDENTIFIER_SWIFT_CODE -> startActivity(Intent(this, SwiftCodeListActivity::class.java))
            IDENTIFIER_STOCK_MARKET -> startActivity(
                Intent(
                    this,
                    StockMarketListActivity::class.java
                )
            )
            IDENTIFIER_BANK_LOANS -> startActivity(Intent(this, BankLoanTypeListActivity::class.java))
            IDENTIFIER_BANK -> startActivity(Intent(this, BankListActivity::class.java))
            IDENTIFIER_CURRENCY_RATES -> {
                val bundle = CommonWebViewActivity.createIntent(
                    this,
                    navRail.content?.url,
                    getString(R.string.currency_rates)
                )
                startActivity(
                    Intent(this, CommonWebViewActivity::class.java).putExtra(
                        WEBVIEW_BUNDLE,
                        bundle
                    )
                )
            }

            IDENTIFIER_ROUTING_NO -> startActivity(
                Intent(
                    this,
                    RoutingBankListActivity::class.java
                )
            )
        }
    }

    override fun onSwiftCodeItemClick(bank: Bank) {
        appAnalytics.registerEvent(
            APP_MENU_ITEM_TAPPED,
            appAnalytics.setData(APP_MENU_ITEM_TAPPED, IDENTIFIER_SWIFT_CODE)
        )
        startActivity(Intent(this, SwiftCodeListActivity::class.java))
    }

    override fun onLoanTypeClick(loanType: LoanType) {
        // empty implementation
    }

    override fun onPopularLoanClick(popularLoan: PopularLoan) {
        appAnalytics.registerEvent(
            POPULAR_LOAN_ITEM_TAPPED,
            appAnalytics.setData(POPULAR_LOAN_ITEM_TAPPED, popularLoan.loanName)
        )
        val bundle = CommonWebViewActivity.createIntent(
            this,
            popularLoan.navigationUrl,
            popularLoan.loanName
        )
        startActivity(
            Intent(this, CommonWebViewActivity::class.java).putExtra(
                WEBVIEW_BUNDLE,
                bundle
            )
        )
    }
}