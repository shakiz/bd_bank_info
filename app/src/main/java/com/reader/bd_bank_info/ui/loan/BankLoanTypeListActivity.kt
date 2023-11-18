package com.reader.bd_bank_info.ui.loan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.databinding.ActivityBankLoanTypeListBinding
import com.reader.bd_bank_info.ui.adapters.BankLoanTypeItemAdapter
import com.reader.bd_bank_info.ui.commonwebview.CommonWebViewActivity
import com.reader.bd_bank_info.utils.BANK_LOAN_TYPE_TAPPED
import com.reader.bd_bank_info.utils.IDENTIFIER_BANK_LOANS
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.WEBVIEW_BUNDLE
import com.reader.bd_bank_info.utils.dimenSize

class BankLoanTypeListActivity : AppCompatActivity(), BankLoanTypeItemAdapter.BankLoanTypeCallBack {

    private lateinit var binding: ActivityBankLoanTypeListBinding
    private lateinit var viewModel: BankLoanViewModel
    private val bankLoanTypeItemAdapter = BankLoanTypeItemAdapter()
    private val analytics = AppInjector.getAnalytics(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankLoanTypeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[BankLoanViewModel::class.java]

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
    }

    private fun initObservers() {
        viewModel.onBankTypeListFetched().observe(this) { bankLoanTypeList ->
            bankLoanTypeList?.let {
                bankLoanTypeItemAdapter.addItems(it)
            }
        }
    }

    private fun setupBankListView() {
        binding.rvBankLoanTypeList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvBankLoanTypeList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvBankLoanTypeList.adapter = bankLoanTypeItemAdapter
        bankLoanTypeItemAdapter.setItemClickListener(this)
    }

    override fun onLoanTypeClick(loanType: LoanType) {
        analytics.registerEvent(
            IDENTIFIER_BANK_LOANS,
            analytics.setData(
                BANK_LOAN_TYPE_TAPPED,
                loanType.loanTypeName?.lowercase()?.replace(" ", "_")
            )
        )

        val bundle = CommonWebViewActivity.createIntent(this, loanType.navigationUrl, loanType.loanTypeName)
        startActivity(Intent(this, CommonWebViewActivity::class.java).putExtra(WEBVIEW_BUNDLE, bundle))
    }
}