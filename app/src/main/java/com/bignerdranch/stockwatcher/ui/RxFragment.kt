package com.bignerdranch.stockwatcher.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

private val EXTRA_RX_REQUEST_IN_PROGRESS = "EXTRA_RX_REQUEST_IN_PROGRESS"

abstract class RxFragment : Fragment() {

    lateinit var compositeDisposable: CompositeDisposable
    var requestInProgress: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        savedInstanceState?.let {
            requestInProgress = it.getBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, requestInProgress)
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
