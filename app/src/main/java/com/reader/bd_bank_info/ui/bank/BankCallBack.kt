package com.reader.bd_bank_info.ui.bank

import com.reader.bd_bank_info.data.model.Bank

interface BankCallBack {
    fun onItemClick(bank: Bank)
    fun onMailClicked(bank: Bank)
    fun onHotlineNumberCalled(bank: Bank)
    fun onRoutingItemClick(bank: Bank)
}