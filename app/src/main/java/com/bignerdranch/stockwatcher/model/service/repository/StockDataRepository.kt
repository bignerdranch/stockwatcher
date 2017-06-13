package com.bignerdranch.stockwatcher.model.service.repository

import com.bignerdranch.stockwatcher.model.service.StockInfoForSymbol
import com.bignerdranch.stockwatcher.model.service.StockInfoResponse
import com.bignerdranch.stockwatcher.model.service.StockService
import com.bignerdranch.stockwatcher.model.service.StockSymbol
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.util.concurrent.TimeUnit

private val CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL = "getStockInfoForSymbol"
private val CACHE_PREFIX_GET_STOCK_SYMBOLS = "lookupStockSymbols"
private val CACHE_PREFIX_GET_STOCK_INFO = "stockInfo"

class StockDataRepository(private val service: StockService) : BaseRepository() {

    fun getStockInfoForSymbol(symbol: String): Observable<StockInfoForSymbol> {
        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL, symbol)
        val combineLatest = Observable.combineLatest(lookupStockSymbol(symbol),
                fetchStockInfoFromSymbol(symbol),
                BiFunction(::StockInfoForSymbol))
        return cacheObservable(CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL + symbol, combineLatest)
    }

    private fun fetchStockInfoFromSymbol(symbol: String): Observable<StockInfoResponse> {
        return lookupStockSymbol(symbol)
                .flatMap { stockSymbol -> getStockInfo(stockSymbol.symbol) }
    }

    private fun lookupStockSymbol(symbol: String): Observable<StockSymbol> {
        return lookupStockSymbols(symbol).flatMap { Observable.fromIterable(it).take(1) }
    }

    private fun lookupStockSymbols(symbol: String): Observable<List<StockSymbol>> {
        Timber.i("%s, symbol: %s", CACHE_PREFIX_GET_STOCK_SYMBOLS, symbol)
        return cacheObservable(CACHE_PREFIX_GET_STOCK_SYMBOLS + symbol, service.lookupStock(symbol).cache())
    }

    private fun getStockInfo(symbol: String): Observable<StockInfoResponse> {
        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO, symbol)
        val observableToCache = service.stockInfo(symbol).delay(3, TimeUnit.SECONDS).cache()
        return cacheObservable(CACHE_PREFIX_GET_STOCK_INFO + symbol, observableToCache)
    }


}