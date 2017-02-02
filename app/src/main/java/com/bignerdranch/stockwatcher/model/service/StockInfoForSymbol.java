package com.bignerdranch.stockwatcher.model.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockInfoForSymbol {
    private StockSymbol stockSymbol;
    private StockInfoResponse stockInfoResponse;
}
