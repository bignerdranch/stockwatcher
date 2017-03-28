package com.bignerdranch.stockwatcher.model.service.repository

import android.support.v4.util.LruCache

import io.reactivex.Observable

abstract class BaseRepository {

    private var apiObservables = createLruCache()

    private fun createLruCache(): LruCache<String, Observable<*>> {
        return LruCache(50)
    }

    fun <T> cacheObservable(symbol: String, observable: Observable<T>): Observable<T> {
        var cachedObservable: Observable<T>? = apiObservables.get(symbol) as Observable<T>
        if (cachedObservable != null) {
            return cachedObservable
        }
        cachedObservable = observable
        updateCache(symbol, cachedObservable)
        return cachedObservable
    }

    private fun <T> updateCache(stockSymbol: String, observable: Observable<T>) {
        apiObservables.put(stockSymbol, observable)
    }

    //remove cache for just one symbol
    fun removeCache(symbol: String) {
        apiObservables.remove(symbol)
    }

    //clear cache for all symbols
    fun clearCache() {
        apiObservables = createLruCache()
    }

}
