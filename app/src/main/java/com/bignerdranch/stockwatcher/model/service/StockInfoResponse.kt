package com.bignerdranch.stockwatcher.model.service

import com.squareup.moshi.Json

data class StockInfoResponse(
        @Json(name = "Status") val status: String,
        @Json(name = "Name") val name: String,
        @Json(name = "LastPrice") val lastPrice: String,
        @Json(name = "Change") val change: Double,
        @Json(name = "ChangePercent") val changePercent: Double,
        @Json(name = "MarketCap") val marketCap: Long,
        @Json(name = "Volume") val volume: Long,
        @Json(name = "ChangeYTD") val changeYTD: Double,
        @Json(name = "ChangePercentYTD") val changePercentYTD: Double,
        @Json(name = "High") val high: Double,
        @Json(name = "Low") val low: Double,
        @Json(name = "Open") val open: Double
)

