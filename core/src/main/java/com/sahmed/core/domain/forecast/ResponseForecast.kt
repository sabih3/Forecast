package com.sahmed.core.domain.forecast

data class ResponseForecast(
    val cnt: Int,
    val cod: String,
    val message: Int,
    val list:List<Forecast>,
    val city:City
)