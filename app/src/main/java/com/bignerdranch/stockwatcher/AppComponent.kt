package com.bignerdranch.stockwatcher

import com.bignerdranch.stockwatcher.ui.StockInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(fragment: StockInfoFragment)
}
