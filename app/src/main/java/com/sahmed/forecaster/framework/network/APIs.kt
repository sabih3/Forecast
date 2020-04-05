package com.sahmed.forecaster.framework.network

import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather
import com.sahmed.core.domain.forecast.ResponseForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface APIs {

    @GET("data/2.5/weather")
    suspend fun getCitiesWeather(@Query("q") cityName:String): ResponseCityWeather


    @GET("/data/2.5/forecast")
    fun getForecast(@Query("lat") lat:String,@Query("lon")lon:String): Call<ResponseForecast>

    @GET("data/2.5/group")
    fun getWeatherOfCities(@Query("id") comma_separated_ids:String):Call<ResponseCityWeather>

}