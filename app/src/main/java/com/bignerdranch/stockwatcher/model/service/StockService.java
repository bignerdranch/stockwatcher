package com.bignerdranch.stockwatcher.model.service;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class StockService {

    private final HttpLoggingInterceptor httpLoggingInterceptor;
    private final ServiceConfig serviceConfig;
    private final StockServiceInterface service;

    interface StockServiceInterface {
        @GET("Quote/json")
        Observable<StockInfoResponse> stockInfo(@Query("symbol") String symbol);

        @GET("Lookup/json")
        Observable<List<StockSymbol>> lookupStock(@Query("input") String symbol);
    }

    public StockService(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
        httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        service = buildService();
    }

    public Observable<StockInfoResponse> stockInfo(String symbol) {
        return service.stockInfo(symbol);
    }

    public Observable<List<StockSymbol>> lookupStock(String symbol) {
        return service.lookupStock(symbol);
    }

    private StockServiceInterface buildService() {
        return getDefaultRetrofitBuilder().build().create(StockServiceInterface.class);
    }

    private Retrofit.Builder getDefaultRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(serviceConfig.getBaseServiceUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClientBuilder().build())
                .addConverterFactory(GsonConverterFactory.create());
    }

    private OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain
                            .request()
                            .newBuilder()
                            .addHeader(CONTENT_TYPE, "application/json")
                            .build();
                    return chain.proceed(request);
                });
    }

}
