package com.reader.bd_bank_info.ui.routings

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivityRoutingBankListBinding
import com.reader.bd_bank_info.network.InternetConnectivity
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.ui.bank.BankCallBack
import com.reader.bd_bank_info.utils.BANK_LIST_ITEM_VIEW_TYPE_ROUTING
import com.reader.bd_bank_info.utils.BANK_NAME
import com.reader.bd_bank_info.utils.BANK_ROUTING_TAPPED
import com.reader.bd_bank_info.utils.BANK_SEARCH_TAPPED
import com.reader.bd_bank_info.utils.ITEM_BANK
import com.reader.bd_bank_info.utils.NO_INTERNET_DIALOG
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize
import com.reader.bd_bank_info.utils.hideSoftKeyboard

class RoutingBankListActivity : AppCompatActivity(), BankCallBack {

    private lateinit var binding: ActivityRoutingBankListBinding
    private lateinit var viewModel: RoutingViewModel
    private val appAnalytics = AppInjector.getAnalytics(this)

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

    override fun onItemClick(bank: Bank) {

    }

    private fun showNoInternetDialog(){
        val dialog = Dialog(this, android.R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.reader.bd_bank_info.R.layout.dialog_no_internet)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)


        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window!!.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        dialog.show()

        val tvTryAgain = dialog.findViewById<TextView>(com.reader.bd_bank_info.R.id.btnTryAgain)
        tvTryAgain.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onMailClicked(bank: Bank) {
        //empty implementation
    }

    override fun onHotlineNumberCalled(bank: Bank) {
        //empty implementation
    }

    override fun onRoutingItemClick(bank: Bank) {
        if(InternetConnectivity.checkConnectivity(this)){
            appAnalytics.registerEvent(BANK_ROUTING_TAPPED, appAnalytics.setData(BANK_ROUTING_TAPPED, bank.bankName))
            startActivity(Intent(this, RoutingDetailsActivity::class.java).putExtra(ITEM_BANK, bank))
        } else {
            appAnalytics.registerEvent(NO_INTERNET_DIALOG, appAnalytics.setData(NO_INTERNET_DIALOG, BANK_ROUTING_TAPPED))
            showNoInternetDialog()
        }
    }
}