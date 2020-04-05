package com.sahmed.forecaster.framework

import com.sahmed.core.data.CityWeathersDataSource
import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather
import com.sahmed.forecaster.framework.network.*

class CityWeathersRepositoryImpl():CityWeathersDataSource{
    override suspend fun getWeatherOfCity(cityName: String): ResponseCityWeather? {

        var cityWeather : ResponseCityWeather? = null
        val apis = RestClient.getInstance()

        try{
            val citiesWeather = apis.getCitiesWeather(cityName)

//            if(citiesWeather.isSuccessful){
               return citiesWeather
//            }
        }catch (exc:Throwable){
            exc.cause
        }

        return cityWeather
    }
}



