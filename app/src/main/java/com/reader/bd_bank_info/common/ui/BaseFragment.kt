package com.reader.bd_bank_info.common.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.reader.bd_bank_info.R

abstract class BaseFragment: Fragment() {

    protected var fragmentContainer: FragmentContainer? = null
    protected open var screenName: String = ""

    private var wentToBackstack = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContainer = context as? FragmentContainer
    }

    override fun onStart() {
        super.onStart()
        // FirebaseAnalytics.getInstance().setCurrentScreen(activity, screenName, screenName)
    }

    override fun onDestroyView() {
        wentToBackstack = true
        super.onDestroyView()
    }

    /**
     * Subclass may need to handle on back press event
     * return true if subclass consumed back press
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    protected fun isRestoredFromBackstack(): Boolean {
        return wentToBackstack
    }

    protected fun observeCommonData(viewModel: BaseViewModel) {
        viewModel.onConnectionError().observe(viewLifecycleOwner) {
            view?.let { anchorView ->
                fragmentContainer?.showSnackMessage(
                    message = getString(R.string.msg_offline),
                    anchor = anchorView
                )
            }
        }

        viewModel.onRemoteMessageReceived().observe(viewLifecycleOwner) { message ->
            view?.let { anchorView ->
                message?.let {
                    fragmentContainer?.showSnackMessage(
                        message = it,
                        anchor = anchorView
                    )
                }
            }
        }

        viewModel.onUnknownError().observe(viewLifecycleOwner) {
            view?.let { anchorView ->
                fragmentContainer?.showSnackMessage(
                    message = getString(R.string.error_msg_unknown_error),
                    anchor = anchorView
                )
            }
        }

        viewModel.onUnauthorizedError().observe(viewLifecycleOwner) {
            fragmentContainer?.navigateToLoginScreen()
        }
    }
}