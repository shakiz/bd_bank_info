package com.reader.bd_bank_info.ui.bank

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.databinding.ActivityBankDetailsBinding
import com.reader.bd_bank_info.ui.commonwebview.CommonWebViewActivity
import com.reader.bd_bank_info.utils.*

class BankDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankDetailsBinding
    private lateinit var viewModel: BankViewModel
    private var bank: Bank? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bank =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(ITEM_BANK, Bank::class.java)
            } else {
                intent.getParcelableExtra(ITEM_BANK)
            }

        viewModel =
            ViewModelProvider(this)[BankViewModel::class.java]

        initView()
        initListeners()

        viewModel.fetchBankList()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = bank?.bankName
        setupBankDetailsUI()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.llSwiftCode.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(bank?.swiftCode, bank?.swiftCode)
            clipboard.setPrimaryClip(clip)
            showLongToast(getString(R.string.swift_code_copied))
        }

        binding.llStockCode.setOnClickListener {
            val bundle = CommonWebViewActivity.createIntent(this, "$STOCK_MARKET_BASE_URL${bank?.stockCode}", bank?.bankName)
            startActivity(Intent(this, CommonWebViewActivity::class.java).putExtra(WEBVIEW_BUNDLE, bundle))
        }

        binding.llHotlineNumber.setOnClickListener {
            makeCall(bank?.hotlineNo.orZero())
        }

        binding.llOverseas.setOnClickListener {
            makeCall(bank?.hotlinePhoneNo?.toInt().orZero())
        }

        binding.llEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_VIEW, Uri.fromParts(
                    "mailto", bank?.email, null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Write your subject here.")
            startActivity(Intent.createChooser(emailIntent, null))
        }
    }

    private fun setupBankDetailsUI(){
        bank?.let {
            it.bankIconRes?.let { res ->
                binding.ivBankLogo.background = (ContextCompat.getDrawable(this, res))
            }
            binding.tvBankDetails.text = getString(R.string.bank_details_with_x_values, it.legalStatus, it.establishedDate, it.bankType, it.origin, it.corporateAddress)
            binding.tvHotlineNumber.text = getString(R.string.call_for_help_x, it.hotlineNo)
            binding.tvBankEmail.text = getString(R.string.send_mail_at_x, it.email)
            binding.tvBankCorporateAddress.text = getString(R.string.address_x, it.corporateAddress)
            binding.tvBankSwiftCode.text = getString(R.string.swift_code_x, it.swiftCode)
            binding.tvBankStockCode.text = getString(R.string.stock_code_x, it.stockCode)
            binding.tvBankOverseasNo.text = getString(R.string.for_overseas_x, it.hotlinePhoneNo)
        }
    }

    private fun makeCall(number: Int){
        if(number <= 0){
            return
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val uri = "tel:$number"
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(uri)
            showLongToast(getString(R.string.calling_x_hotline_number, number))
            startActivity(callIntent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.please_allow_call_permission),
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
}