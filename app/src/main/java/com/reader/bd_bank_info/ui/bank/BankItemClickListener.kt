package com.reader.bd_bank_info.ui.bank

import com.reader.bd_bank_info.data.model.Bank

interface BankItemClickListener {
    fun onItemClick(bank: Bank)
}