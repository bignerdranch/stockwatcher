package com.bignerdranch.stockwatcher.model.service


import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class StockService(retrofit: Retrofit) {

    private val service: StockServiceInterface

    init {
        service = retrofit.create(StockServiceInterface::class.java)
    }

    internal interface StockServiceInterface {
        @GET("Quote/json")
        fun stockInfo(@Query("symbol") symbol: String): Observable<StockInfoResponse>

        @GET("Lookup/json")
        fun lookupStock(@Query("input") symbol: String): Observable<List<StockSymbol>>
    }

    fun stockInfo(symbol: String): Observable<StockInfoResponse> {
        return service.stockInfo(symbol)
    }

    fun lookupStock(symbol: String): Observable<List<StockSymbol>> {
        return service.lookupStock(symbol)
    }
}
