package com.bignerdranch.stockwatcher.util;

import com.bignerdranch.stockwatcher.ui.RxFragment;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    public static final String LOADING_MESSAGE = "Loading";

    public static <T> ObservableTransformer<T, T> applyUIDefaults(RxFragment rxFragment) {
        return upstream -> upstream
                .compose(RxUtil.addToCompositeDisposable(rxFragment))
                .compose(RxUtil.applySchedulers())
                .compose(RxUtil.applyRequestStatus(rxFragment))
                .compose(RxUtil.showLoadingDialog(rxFragment));
    }

    private static final ObservableTransformer schedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    private static <T> ObservableTransformer<T, T> addToCompositeDisposable(RxFragment rxFragment) {
        return upstream -> upstream.doOnSubscribe(disposable -> rxFragment.getCompositeDisposable().add(disposable));
    }

    private static <T> ObservableTransformer<T, T> applyRequestStatus(RxFragment rxFragment) {
        return upstream -> upstream.doOnSubscribe(disposable -> rxFragment.setRequestInProgress(true))
                .doOnTerminate(() -> rxFragment.setRequestInProgress(false));
    }

    private static <T> ObservableTransformer<T, T> showLoadingDialog(RxFragment rxFragment) {
        return observable -> observable
                .doOnSubscribe(disposable -> DialogUtils.showProgressDialog(rxFragment.getFragmentManager(), LOADING_MESSAGE))
                .doOnTerminate(() -> DialogUtils.hideProgressDialog(rxFragment.getFragmentManager()));
    }
}
