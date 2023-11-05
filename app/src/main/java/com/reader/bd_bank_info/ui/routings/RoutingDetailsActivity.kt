package com.reader.bd_bank_info.ui.routings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivityRoutingDetailsBinding
import com.reader.bd_bank_info.ui.adapters.RoutingItemAdapter
import com.reader.bd_bank_info.utils.ITEM_BANK
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize
import com.reader.bd_bank_info.utils.hideSoftKeyboard
import com.reader.bd_bank_info.utils.orZero
import com.reader.bd_bank_info.utils.setVisibility
import com.reader.bd_bank_info.utils.showLongToast

class RoutingDetailsActivity : AppCompatActivity(), RoutingItemAdapter.RoutingCopyClickListener{

    private lateinit var binding: ActivityRoutingDetailsBinding
    private lateinit var viewModel: RoutingViewModel
    private val routingItemAdapter = RoutingItemAdapter()
    private var bank: Bank? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bank =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(ITEM_BANK, Bank::class.java)
            } else {
                intent.getParcelableExtra(ITEM_BANK)
            }

        viewModel =
            ViewModelProvider(this)[RoutingViewModel::class.java]

        initView()
        initListeners()
        initObservers()

        viewModel.fetchRoutingByBankId(bankId = bank?.bankId.orZero())
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        setupRoutingBankListView()
        binding.toolbar.title = bank?.bankName
        binding.searchLayout.etSearchName.hint = getString(com.reader.bd_bank_info.R.string.search_branch_name)
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.searchLayout.ibSearchButton.setOnClickListener {
            hideSoftKeyboard()
            viewModel.searchBankItemForRoutingByBranch(binding.searchLayout.etSearchName.text.toString())
        }

        binding.searchLayout.ibRefreshButton.setOnClickListener {
            hideSoftKeyboard()
            viewModel.fetchRoutingByBankId(bankId = bank?.bankId.orZero())
        }
    }

    private fun initObservers() {
        viewModel.onDataLoadingStateChanged().observe(this){ isLoading ->
            binding.loadingLayout.llLoading.setVisibility(isLoading)
        }

        viewModel.onRoutingListFetched().observe(this){ routings ->
            routings?.let {
                routingItemAdapter.addItems(it)
            }
        }
    }

    private fun setupRoutingBankListView() {
        binding.rvRoutings.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvRoutings.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvRoutings.adapter = routingItemAdapter
        routingItemAdapter.setCopyClickListener(this)
    }

    override fun onCopyClicked(routingNo: Int) {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(routingNo.toString(), routingNo.toString())
        clipboard.setPrimaryClip(clip)
        showLongToast(getString(com.reader.bd_bank_info.R.string.routing_no_copied))
    }
}