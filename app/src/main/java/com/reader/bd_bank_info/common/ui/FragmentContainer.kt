package com.reader.bd_bank_info.common.ui

import android.view.View

interface FragmentContainer {

    fun showSnackMessage(
        message: String? = null,
        anchor: View? = null,
        action: String? = null,
        onAction: (() -> Unit)? = null,
        onDismissed: (() -> Unit)? = null
    )

    fun showToastMessage(message: String? = null)

    fun navigateToLoginScreen()
}