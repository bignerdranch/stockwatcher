package com.bignerdranch.stockwatcher.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bignerdranch.stockwatcher.R
import com.bignerdranch.stockwatcher.StockWatcherApplication
import com.bignerdranch.stockwatcher.model.service.StockInfoForSymbol
import com.bignerdranch.stockwatcher.model.service.repository.StockDataRepository
import com.bignerdranch.stockwatcher.util.applyUIDefaults
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_stock_info.*
import java.util.*
import javax.inject.Inject

class StockInfoFragment : RxFragment() {

    @Inject
    internal lateinit var stockDataRepository: StockDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        StockWatcherApplication.getAppComponent(context).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_stock_info, container, false)
        fetch_data_button.apply {
            setOnClickListener {
                error_message.visibility = View.GONE
                loadRxData()
            }
        }

        ticker_symbol.setOnEditorActionListener { _, _, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                loadRxData()
            }
            true
        }

        clear_cache_button.setOnClickListener {
            stockDataRepository.clearCache()
            Toast.makeText(context, "observable cache cleared!", Toast.LENGTH_LONG).show()
        }
        return rootView
    }

    override fun loadRxData() {
        Observable.just(ticker_symbol.text.toString())
                .filter { it.isNotEmpty() }
                .singleOrError()
                .toObservable()
                .flatMap({ stockDataRepository.getStockInfoForSymbol(it) })
                .applyUIDefaults(this)
                .subscribe({ this.displayStockResults(it) }, { this.displayErrors(it) })
    }

    private fun displayErrors(throwable: Throwable) {
        var message = throwable.message
        if (throwable is NoSuchElementException) {
            message = "Enter a stock symbol first!!"
        }
        error_message.visibility = View.VISIBLE
        error_message.text = message
    }

    private fun displayStockResults(stockInfoForSymbol: StockInfoForSymbol) {
        stock_value.text = stockInfoForSymbol.toString()
    }
}
