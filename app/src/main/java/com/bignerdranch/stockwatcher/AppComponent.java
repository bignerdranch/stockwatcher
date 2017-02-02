package com.bignerdranch.stockwatcher;

import com.bignerdranch.stockwatcher.ui.StockInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(StockInfoFragment fragment);
}
