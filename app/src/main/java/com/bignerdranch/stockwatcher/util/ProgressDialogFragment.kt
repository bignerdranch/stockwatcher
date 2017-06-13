package com.bignerdranch.stockwatcher.util

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = ProgressDialog(context)
        val message = arguments.getString(ARG_MESSAGE)
        dialog.setMessage(message)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        return dialog
    }

    companion object {

        private val ARG_MESSAGE = "message"

        fun newInstance(message: String): ProgressDialogFragment {
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            val fragment = ProgressDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
