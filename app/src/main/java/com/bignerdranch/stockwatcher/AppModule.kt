package com.bignerdranch.stockwatcher

import com.bignerdranch.stockwatcher.model.service.ContentTypeHeaderInterceptor
import com.bignerdranch.stockwatcher.model.service.ServiceConfig
import com.bignerdranch.stockwatcher.model.service.StockService
import com.bignerdranch.stockwatcher.model.service.repository.StockDataRepository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private val STOCK_SERVICE_ENDPOINT = "http://dev.markitondemand.com/MODApis/Api/v2/"
private val HTTP_READ_TIMEOUT_SECONDS = 60
private val HTTP_CONNECT_TIMEOUT_SECONDS = 60

@Module
internal class AppModule {


    @Provides
    @Singleton
    fun provideStockDataRepository(stockService: StockService): StockDataRepository {
        return StockDataRepository(stockService)
    }

    @Provides
    fun provideStockService(retrofit: Retrofit): StockService {
        return StockService(retrofit)
    }

    @Provides
    fun provideServiceConfig(): ServiceConfig {
        return ServiceConfig(STOCK_SERVICE_ENDPOINT)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, serviceConfig: ServiceConfig): Retrofit {
        return Retrofit.Builder()
                .baseUrl(serviceConfig.baseServiceUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(HTTP_READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .connectTimeout(HTTP_CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(ContentTypeHeaderInterceptor())
                .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}
