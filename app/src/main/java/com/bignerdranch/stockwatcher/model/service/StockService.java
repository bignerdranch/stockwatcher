package com.bignerdranch.stockwatcher.model.service;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class StockService {

    private final StockServiceInterface service;

    interface StockServiceInterface {
        @GET("Quote/json")
        Observable<StockInfoResponse> stockInfo(@Query("symbol") String symbol);

        @GET("Lookup/json")
        Observable<List<StockSymbol>> lookupStock(@Query("input") String symbol);
    }

    public StockService(Retrofit retrofit) {
        service = retrofit.create(StockServiceInterface.class);
    }

    public Observable<StockInfoResponse> stockInfo(String symbol) {
        return service.stockInfo(symbol);
    }

    public Observable<StockSymbol> lookupStock(String symbol) {
        return service.lookupStock(symbol)
                .concatMap(Observable::fromIterable)
                .firstOrError()
                .toObservable();
    }
}
