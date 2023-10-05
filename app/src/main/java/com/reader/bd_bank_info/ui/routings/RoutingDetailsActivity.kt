package com.reader.bd_bank_info.ui.routings

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intuit.sdp.R
import com.reader.bd_bank_info.databinding.ActivityRoutingDetailsBinding
import com.reader.bd_bank_info.ui.adapters.BankVerticalItemAdapter
import com.reader.bd_bank_info.utils.SpaceItemDecoration
import com.reader.bd_bank_info.utils.dimenSize

class RoutingDetailsActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRoutingDetailsBinding
    private lateinit var viewModel: RoutingViewModel
    private val bankItemAdapter = BankVerticalItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutingDetailsBinding.inflate(layoutInflater)
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
        binding.searchLayout.etSearchName.hint = getString(com.reader.bd_bank_info.R.string.search_branch_name)
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initObservers() {

    }

    private fun setupRoutingBankListView() {
        binding.rvRoutings.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvRoutings.addItemDecoration(SpaceItemDecoration(this.dimenSize(R.dimen._8sdp)))
        binding.rvRoutings.adapter = bankItemAdapter
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