package com.bignerdranch.stockwatcher.model.service.repository

import android.support.v4.util.LruCache

import io.reactivex.Observable

abstract class BaseRepository {

    private var apiObservables = createLruCache()

    private fun createLruCache(): LruCache<String, Observable<*>> {
        return LruCache(50)
    }

    fun <T> cacheObservable(symbol: String, observable: Observable<T>): Observable<T> {
        val cachedObservable = apiObservables.get(symbol) ?: observable
        updateCache(symbol, cachedObservable)
        return cachedObservable as Observable<T>
    }

    private fun <T> updateCache(stockSymbol: String, observable: Observable<T>) {
        apiObservables.put(stockSymbol, observable)
    }

    //clear cache for all symbols
    fun clearCache() {
        apiObservables = createLruCache()
    }

}