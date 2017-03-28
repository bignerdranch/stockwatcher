package com.bignerdranch.stockwatcher.util

import android.support.v4.app.FragmentManager

object DialogUtils {

    private val TAG_DIALOG_PROGRESS = "dialog_progress"

    internal fun showProgressDialog(fragmentManager: FragmentManager,
                                    message: String) {
        if (fragmentManager.findFragmentByTag(DialogUtils.TAG_DIALOG_PROGRESS) != null) {
            hideProgressDialog(fragmentManager)
        }
        val dialog = ProgressDialogFragment.newInstance(message)
        dialog.isCancelable = false
        dialog.show(fragmentManager, TAG_DIALOG_PROGRESS)

    }

    internal fun hideProgressDialog(fragmentManager: FragmentManager) {
        val fragment = fragmentManager.findFragmentByTag(TAG_DIALOG_PROGRESS)
        if (fragment is ProgressDialogFragment) {
            fragment.dismissAllowingStateLoss()
        }
    }

}
