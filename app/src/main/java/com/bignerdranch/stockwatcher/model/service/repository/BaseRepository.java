package com.bignerdranch.stockwatcher.model.service.repository;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import io.reactivex.Observable;

abstract class BaseRepository {

    private LruCache<String, Observable<?>> apiObservables = createLruCache();

    @NonNull
    private LruCache<String, Observable<?>> createLruCache() {
        return new LruCache<>(50);
    }

    @SuppressWarnings("unchecked")
    <T> Observable<T> cacheObservable(String symbol, Observable<T> observable) {
        Observable<T> cachedObservable = (Observable<T>) apiObservables.get(symbol);
        if (cachedObservable != null) {
            return cachedObservable;
        }
        cachedObservable = observable;
        updateCache(symbol, cachedObservable);
        return cachedObservable;
    }

    private <T> void updateCache(String stockSymbol, Observable<T> observable) {
        apiObservables.put(stockSymbol, observable);
    }

    //remove cache for just one symbol
    public void removeCache(String symbol) {
        apiObservables.remove(symbol);
    }

    //clear cache for all symbols
    public void clearCache() {
        apiObservables = createLruCache();
    }

}
