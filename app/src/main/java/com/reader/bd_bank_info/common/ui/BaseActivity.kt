package com.reader.bd_bank_info.common.ui

import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.common.utils.showLongToast

abstract class BaseActivity: AppCompatActivity(), FragmentContainer,
    ConnectionStateMonitor.ConnectionChangeListener {

    private val connectivityMonitor by lazy { ConnectionStateMonitor(this) }

    override fun onStart() {
        super.onStart()
        connectivityMonitor.enable(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityMonitor.disable(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSnackMessage(
        message: String?,
        anchor: View?,
        action: String?,
        onAction: (() -> Unit)?,
        onDismissed: (() -> Unit)?
    ) {
        message?.let {
            val snackBar = Snackbar.make(anchor ?: getContentView(), message, Snackbar.LENGTH_LONG)
            snackBar.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    onDismissed?.invoke()
                }
            })

            if (action != null) {
                snackBar.setAction(action) { onAction?.invoke() }
            }

            snackBar.show()
        }
    }

    override fun showToastMessage(message: String?) {
        showLongToast(message)
    }

    override fun navigateToLoginScreen() {
        // TODO navigate to login activity/fragment
    }

    override fun onConnectionLost() {
        // TODO show offline dialog
    }

    override fun onReconnected() {
        // TODO hide offline dialog
    }

    private fun getContentView(): ViewGroup {
        return findViewById(android.R.id.content)
    }

    protected fun observeCommonData(viewModel: BaseViewModel, rootView: CoordinatorLayout? = null) {
        viewModel.onConnectionError().observe(this) {
            showSnackMessage(
                message = getString(R.string.msg_offline),
                anchor = rootView
            )
        }

        viewModel.onRemoteMessageReceived().observe(this) { message ->
            message?.let { showSnackMessage(message = it, anchor = rootView) }
        }

        viewModel.onUnknownError().observe(this) {
            showSnackMessage(
                message = getString(R.string.error_msg_unknown_error),
                anchor = rootView
            )
        }

        viewModel.onUnauthorizedError().observe(this) {
            navigateToLoginScreen()
        }
    }
}

