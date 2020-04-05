package com.sahmed.core.interactors

import com.sahmed.core.data.CityWeathersRepository

class GetCityWeathers(private val cityWeathersRepository: CityWeathersRepository){

    suspend fun getCityWeathers(cityName: String) =
        cityWeathersRepository.getCityWeathers(cityName)
}