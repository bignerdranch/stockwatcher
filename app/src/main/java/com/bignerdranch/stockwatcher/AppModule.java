package com.bignerdranch.stockwatcher;

import com.bignerdranch.stockwatcher.model.service.ContentTypeHeaderInterceptor;
import com.bignerdranch.stockwatcher.model.service.ServiceConfig;
import com.bignerdranch.stockwatcher.model.service.repository.StockDataRepository;
import com.bignerdranch.stockwatcher.model.service.StockService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class AppModule {

    private static final String STOCK_SERVICE_ENDPOINT = "http://dev.markitondemand.com/MODApis/Api/v2/";
    private static final int HTTP_READ_TIMEOUT = 60;
    private static final int HTTP_CONNECT_TIMEOUT = 60;

    @Provides
    @Singleton
    StockDataRepository provideStockDataRepository(StockService stockService) {
        return new StockDataRepository(stockService);
    }

    @Provides
    StockService provideStockService(Retrofit retrofit) {
        return new StockService(retrofit);
    }

    @Provides
    ServiceConfig provideServiceConfig() {
        return new ServiceConfig(STOCK_SERVICE_ENDPOINT);
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client, ServiceConfig serviceConfig) {
        return new Retrofit.Builder()
                .baseUrl(serviceConfig.getBaseServiceUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new ContentTypeHeaderInterceptor())
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
