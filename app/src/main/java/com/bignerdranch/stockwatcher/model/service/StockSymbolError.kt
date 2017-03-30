package com.bignerdranch.stockwatcher.model.service

class StockSymbolError(private val symbol: String) : Error() {
    override val message: String?
        get() = String.format("Error: no StockSymbols found for symbol: %s", symbol)
}
