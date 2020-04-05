package com.sahmed.core.data

class CityWeathersRepository(val cityWeathersDataSource: CityWeathersDataSource) {

    suspend fun getCityWeathers(cityName:String)=
                                          cityWeathersDataSource.getWeatherOfCity(cityName)
}