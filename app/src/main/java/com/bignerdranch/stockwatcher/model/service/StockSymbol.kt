package com.bignerdranch.stockwatcher.model.service

import com.squareup.moshi.Json

data class StockSymbol(
        @Json(name = "Symbol") val symbol: String,
        @Json(name = "Name") val name: String
)
