package com.reader.bd_bank_info.ui.loan

import android.Manifest
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
import com.reader.bd_bank_info.databinding.ActivityBankListBinding
import com.reader.bd_bank_info.databinding.ActivityBankLoanTypeListBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.ui.bank.BankCallBack
import com.reader.bd_bank_info.ui.bank.BankDetailsActivity
import com.reader.bd_bank_info.ui.bank.BankViewModel
import com.reader.bd_bank_info.utils.BANK_DETAILS_TAPPED
import com.reader.bd_bank_info.utils.BANK_HOTLINE_NO_TAPPED
import com.reader.bd_bank_info.utils.BANK_LIST_ITEM_VIEW_TYPE_BANK_LIST
import com.reader.bd_bank_info.utils.BANK_MAIL_TAPPED
import com.reader.bd_bank_info.utils.BANK_NAME
import com.reader.bd_bank_info.utils.BANK_SEARCH_TAPPED
import com.reader.bd_bank_info.utils.ITEM_BANK
import com.reader.bd_bank_info.utils.REQUEST_CALL_CODE
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize
import com.reader.bd_bank_info.utils.hideSoftKeyboard
import com.reader.bd_bank_info.utils.orZero
import com.reader.bd_bank_info.utils.showLongToast

class BankLoanTypeListActivity : AppCompatActivity(), BankCallBack {

    private lateinit var binding: ActivityBankLoanTypeListBinding
    private lateinit var viewModel: BankViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()
    private val analytics = AppInjector.getAnalytics(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankLoanTypeListBinding.inflate(layoutInflater)
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
            analytics.registerEvent(BANK_SEARCH_TAPPED, analytics.setData(BANK_NAME, binding.searchLayout.etSearchName.text.toString()))
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
                bankItemAdapter.addItems(bankList)
            }
        }
    }

    private fun setupBankListView() {
        binding.rvBankList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvBankList.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvBankList.adapter = bankItemAdapter
        bankItemAdapter.setViewType(BANK_LIST_ITEM_VIEW_TYPE_BANK_LIST)
        bankItemAdapter.setItemClickListener(this)
    }

    override fun onItemClick(bank: Bank) {
        analytics.registerEvent(BANK_DETAILS_TAPPED, analytics.setData(BANK_NAME, bank.bankName))
        startActivity(Intent(this, BankDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
    }

    override fun onMailClicked(bank: Bank) {
        analytics.registerEvent(BANK_MAIL_TAPPED, analytics.setData(BANK_NAME, bank.bankName))
        val emailIntent = Intent(
            Intent.ACTION_VIEW, Uri.fromParts(
                "mailto", bank.email, null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Write your subject here.")
        startActivity(Intent.createChooser(emailIntent, null))
    }

    override fun onHotlineNumberCalled(bank: Bank) {
        analytics.registerEvent(BANK_HOTLINE_NO_TAPPED, analytics.setData(BANK_NAME, bank.bankName))

        if(bank.hotlineNo.orZero() <= 0){
            return
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val uri = "tel:${bank.hotlineNo.orZero()}"
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(uri)
            showLongToast(getString(com.reader.bd_bank_info.R.string.calling_x_hotline_number, bank.hotlineNo.orZero()))
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

    override fun onRoutingItemClick(bank: Bank) {
        // empty implementation
    }
}