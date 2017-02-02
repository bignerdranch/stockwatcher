package com.bignerdranch.stockwatcher.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import lombok.Getter;
import lombok.Setter;

public abstract class RxFragment extends Fragment {

    private static final java.lang.String EXTRA_RX_REQUEST_IN_PROGRESS = "EXTRA_RX_REQUEST_IN_PROGRESS";

    @Getter
    @Setter
    private boolean requestInProgress;

    @Getter
    @Setter
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        if (savedInstanceState != null) {
            requestInProgress = savedInstanceState.getBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, requestInProgress);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isRequestInProgress()) {
            loadRxData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    public abstract void loadRxData();

}
