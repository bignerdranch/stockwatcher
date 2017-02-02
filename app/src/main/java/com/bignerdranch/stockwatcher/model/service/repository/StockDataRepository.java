package com.bignerdranch.stockwatcher.model.service.repository;

import com.bignerdranch.stockwatcher.model.service.StockInfoForSymbol;
import com.bignerdranch.stockwatcher.model.service.StockInfoResponse;
import com.bignerdranch.stockwatcher.model.service.StockService;
import com.bignerdranch.stockwatcher.model.service.StockSymbol;
import com.bignerdranch.stockwatcher.model.service.StockSymbolError;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import timber.log.Timber;

public class StockDataRepository extends BaseRepository {

    private static final String CACHE_PREFIX_GET_STOCK_INFO = "stockInfo";
    private static final String CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL = "getStockInfoForSymbol";
    private static final String CACHE_PREFIX_GET_STOCK_SYMBOLS = "lookupStock";

    private final StockService service;

    public StockDataRepository(StockService service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public Observable<StockInfoForSymbol> getStockInfoForSymbol(String symbol) {

        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL, symbol);
        //lookup stock request, with checks for results, or not.
        Observable<StockSymbol> lookupStockObservable = lookupStock(symbol)
                .map(list -> {
                    if (list.size() == 0) {
                        throw new StockSymbolError(symbol);
                    }
                    return list.get(0);
                });
        //stock info request, which depends on the first result from  lookup stock request
        Observable<StockInfoResponse> stockInfoResponseObservable = lookupStockObservable
                .flatMap(stockSymbol -> getStockInfo(stockSymbol.getSymbol()));
        Observable<StockInfoForSymbol> stockInfoForSymbolObservable = Observable.combineLatest(lookupStockObservable,
                stockInfoResponseObservable, StockInfoForSymbol::new);
        //the final result cached
        return cacheObservable(CACHE_PREFIX_GET_STOCK_INFO_FOR_SYMBOL + symbol, stockInfoForSymbolObservable);
    }

    private Observable<List<StockSymbol>> lookupStock(String symbol) {
        Timber.i("%s, symbol: %s", CACHE_PREFIX_GET_STOCK_SYMBOLS, symbol);
        return cacheObservable(CACHE_PREFIX_GET_STOCK_SYMBOLS + symbol, service.lookupStock(symbol).cache());
    }

    private Observable<StockInfoResponse> getStockInfo(String symbol) {
        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_STOCK_INFO, symbol);
        Observable<StockInfoResponse> observableToCache = service
                .stockInfo(symbol)
                //fake a big slow API response
                .delay(3, TimeUnit.SECONDS)
                .cache();
        return cacheObservable(CACHE_PREFIX_GET_STOCK_INFO + symbol, observableToCache);
    }

}
