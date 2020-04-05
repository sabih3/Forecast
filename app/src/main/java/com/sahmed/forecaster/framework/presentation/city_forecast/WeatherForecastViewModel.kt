package com.sahmed.forecaster.framework.presentation.city_forecast

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sahmed.core.domain.forecast.Forecast
import com.sahmed.core.domain.forecast.ResponseForecast
import com.sahmed.forecaster.framework.ForecastRemoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherForecastViewModel(application: Application,
                               val forecastRemoteRepository: ForecastRemoteRepository): AndroidViewModel(application) {

    var forecastData = MutableLiveData<List<Forecast>>()

    fun fetchForecastOfGeoLoc(lat:Double, lon:Double){

        forecastRemoteRepository.getForecast(lat,lon,object:ForecastRemoteRepository.ForecastCallback{
            override fun onSuccess(it: List<Forecast>) {
                forecastData.value = it
           }

            override fun onNetworkIssue() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onEmpty() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }

}