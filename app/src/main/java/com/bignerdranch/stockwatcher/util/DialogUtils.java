package com.bignerdranch.stockwatcher.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class DialogUtils {

    private static final String TAG_DIALOG_PROGRESS = "dialog_progress";

    static void showProgressDialog(FragmentManager fragmentManager,
                                   String message) {
        if (fragmentManager.findFragmentByTag(DialogUtils.TAG_DIALOG_PROGRESS) != null) {
            hideProgressDialog(fragmentManager);
        }
        ProgressDialogFragment dialog = ProgressDialogFragment.newInstance(message);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, TAG_DIALOG_PROGRESS);

    }

    static void hideProgressDialog(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_DIALOG_PROGRESS);
        if (fragment instanceof ProgressDialogFragment) {
            ((ProgressDialogFragment) fragment).dismissAllowingStateLoss();
        }
    }

}
