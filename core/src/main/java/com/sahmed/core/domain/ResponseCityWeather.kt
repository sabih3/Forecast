package com.sahmed.core.domain

data class ResponseCityWeather(
  var cnt:Int,
  var list: List<CityWeathers>
)