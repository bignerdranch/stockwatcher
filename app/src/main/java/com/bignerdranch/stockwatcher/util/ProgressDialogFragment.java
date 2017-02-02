package com.bignerdranch.stockwatcher.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";

    public static ProgressDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        String message = getArguments().getString(ARG_MESSAGE);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }
}
