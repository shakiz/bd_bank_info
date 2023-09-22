package com.reader.bd_bank_info.common.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BaseDialogFragment: DialogFragment() {
    companion object {
        const val BUTTON_POSITIVE = DialogInterface.BUTTON_POSITIVE
        const val BUTTON_NEGATIVE = DialogInterface.BUTTON_NEGATIVE
    }


    protected var dialogActionListener: OnDialogActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogActionListener = context as? OnDialogActionListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(
            requireContext(),
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        ) {
            override fun onBackPressed() {
                super.onBackPressed()
                activity?.onBackPressed()
            }
        }
    }

    interface OnDialogActionListener {

        fun onDialogActionButtonClicked(which: Int, skipAble: Boolean)
    }
}