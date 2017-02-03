package com.bignerdranch.stockwatcher.model.service;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockInfoResponse {

    @SerializedName("Status")
    private String status;

    @SerializedName("Name")
    private String name;

    @SerializedName("LastPrice")
    private double lastPrice;

    @SerializedName("Change")
    private double change;

    @SerializedName("ChangePercent")
    private double changePercent;

    @SerializedName("MarketCap")
    private long marketCap;

    @SerializedName("Volume")
    private long volume;

    @SerializedName("ChangeYTD")
    private double changeYTD;

    @SerializedName("ChangePercentYTD")
    private double changePercentYTD;

    @SerializedName("High")
    private double high;

    @SerializedName("Low")
    private double low;

    @SerializedName("Open")
    private double open;
}
