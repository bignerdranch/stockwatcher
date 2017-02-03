package com.bignerdranch.stockwatcher;

import android.app.Application;
import android.content.Context;

import lombok.Getter;

public class StockWatcherApplication extends Application {

    @Getter
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static StockWatcherApplication get(Context context) {
        return (StockWatcherApplication) context.getApplicationContext();
    }

    public static AppComponent getAppComponent(Context context) {
        StockWatcherApplication stockWatcherApplication = get(context.getApplicationContext());
        return stockWatcherApplication.getAppComponent();
    }

}
