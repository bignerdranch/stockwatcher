package com.bignerdranch.stockwatcher;

import android.app.Application;

import com.bignerdranch.stockwatcher.model.service.ServiceConfig;
import com.bignerdranch.stockwatcher.model.service.repository.StockDataRepository;
import com.bignerdranch.stockwatcher.model.service.StockService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String STOCK_SERVICE_ENDPOINT = "http://dev.markitondemand.com/MODApis/Api/v2/";

    private final Application application;
    private final ServiceConfig serviceConfig;

    AppModule(Application application) {
        this.application = application;
        serviceConfig = new ServiceConfig(STOCK_SERVICE_ENDPOINT);
    }

    @Provides
    @Singleton
    StockDataRepository provideStockDataRepository() {
        StockService stockService = new StockService(serviceConfig);
        return new StockDataRepository(stockService);
    }


}
