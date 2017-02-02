package com.bignerdranch.stockwatcher.model.service;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockSymbol {
    @SerializedName("Symbol")
    String symbol;
    @SerializedName("Name")
    String name;
}
