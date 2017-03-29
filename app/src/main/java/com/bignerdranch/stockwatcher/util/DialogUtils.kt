package com.bignerdranch.stockwatcher.util

import android.support.v4.app.FragmentManager

object DialogUtils {

    private val TAG_DIALOG_PROGRESS = "dialog_progress"

    internal fun showProgressDialog(fragmentManager: FragmentManager, message: String) {
        fragmentManager.findFragmentByTag(DialogUtils.TAG_DIALOG_PROGRESS)?.let {
            hideProgressDialog(fragmentManager)
        }
        ProgressDialogFragment.newInstance(message).apply {
            isCancelable = false
            show(fragmentManager, TAG_DIALOG_PROGRESS)
        }
    }

    internal fun hideProgressDialog(fragmentManager: FragmentManager) {
        val fragment = fragmentManager.findFragmentByTag(TAG_DIALOG_PROGRESS)
        if (fragment is ProgressDialogFragment) {
            fragment.dismissAllowingStateLoss()
        }
    }

}
