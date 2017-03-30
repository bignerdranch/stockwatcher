package com.bignerdranch.stockwatcher.ui

import android.os.Bundle
import android.support.v4.app.Fragment

import io.reactivex.disposables.CompositeDisposable

private val EXTRA_RX_REQUEST_IN_PROGRESS_CONST = "EXTRA_RX_REQUEST_IN_PROGRESS_CONST"

abstract class RxFragment : Fragment() {

    var requestInProgress = false

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            requestInProgress = savedInstanceState.getBoolean(EXTRA_RX_REQUEST_IN_PROGRESS_CONST, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean(EXTRA_RX_REQUEST_IN_PROGRESS_CONST, requestInProgress)
    }

    override fun onResume() {
        super.onResume()
        if (requestInProgress) {
            loadRxData()
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    abstract fun loadRxData()
}
