package com.bignerdranch.stockwatcher

import android.app.Application
import android.content.Context

class StockWatcherApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
    }

    companion object {

        operator fun get(context: Context): StockWatcherApplication {
            return context.applicationContext as StockWatcherApplication
        }

        fun getAppComponent(context: Context): AppComponent {
            val stockWatcherApplication = get(context.applicationContext)
            return stockWatcherApplication.appComponent
        }
    }

}
