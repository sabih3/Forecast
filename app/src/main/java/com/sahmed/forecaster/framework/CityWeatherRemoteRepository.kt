package com.sahmed.forecaster.framework

import androidx.annotation.VisibleForTesting
import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather
import com.sahmed.core.domain.Weather
import com.sahmed.forecaster.framework.network.APIs
import com.sahmed.forecaster.framework.network.BaseCallback
import com.sahmed.forecaster.framework.network.BaseRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherRemoteRepository(val remoteDataSource: APIs):BaseRepo() {

    fun getWeatherOfCities(comma_separated_ids:String,callback: CityWeatherCallback){
        remoteDataSource.getWeatherOfCities(comma_separated_ids).enqueue(object: Callback<ResponseCityWeather> {
            override fun onFailure(call: Call<ResponseCityWeather>, t: Throwable) {
                handleErrorResponse(t,callback)
            }

            override fun onResponse(
                call: Call<ResponseCityWeather>,
                response: Response<ResponseCityWeather>
            ) {
                handleResponse(response,callback)
            }
        })
    }


    @VisibleForTesting
    fun handleResponse(
        response: Response<ResponseCityWeather>, callback: CityWeatherCallback) {
        if(response.isSuccessful){
            if(response.body()!=null){
                callback.onSuccess(response.body()!!.list)
            }else{
                callback.onEmpty()
            }
        }
    }


    interface CityWeatherCallback:BaseCallback{
        fun onSuccess(weather: List<CityWeathers>)
    }
}