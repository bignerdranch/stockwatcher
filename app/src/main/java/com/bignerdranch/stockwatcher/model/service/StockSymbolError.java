package com.bignerdranch.stockwatcher.model.service;

public class StockSymbolError extends Error {

    private final String symbol;

    public StockSymbolError(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getMessage() {
        return String.format("Error: no StockSymbols found for symbol: %s", symbol);
    }
}
