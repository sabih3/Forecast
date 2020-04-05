package com.sahmed.core.data

import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather

interface CityWeathersDataSource {

    suspend fun getWeatherOfCity(cityName:String): ResponseCityWeather?
}