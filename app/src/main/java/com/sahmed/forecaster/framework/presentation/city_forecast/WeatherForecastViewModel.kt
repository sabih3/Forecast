package com.sahmed.forecaster.framework.presentation.city_forecast

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sahmed.core.domain.forecast.Forecast
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository
import com.sahmed.forecaster.framework.ForecastRemoteRepository
import com.sahmed.forecaster.framework.ForecasterViewModel

class WeatherForecastViewModel(application: Application,
                               cityWeatherRemoteRepository: CityWeatherRemoteRepository,
                               forecastRemoteRepository: ForecastRemoteRepository): ForecasterViewModel(application, cityWeatherRemoteRepository,forecastRemoteRepository) {

    private val responseState = MutableLiveData<ForecastResponseState>()
    val forecastData = responseState


    sealed class ForecastResponseState(){
        object Loading:ForecastResponseState()
        data class Success(val response:List<Forecast>):ForecastResponseState()
        object Empty: ForecastResponseState()
        object UnknownFailure: ForecastResponseState()
        object NetworkFailure: ForecastResponseState()
    }

    fun fetchForecastOfGeoLoc(lat:Double, lon:Double){
        setResult(ForecastResponseState.Loading)
        forecastRemoteRepository.getForecast(lat,lon,object:ForecastRemoteRepository.ForecastCallback{
            override fun onSuccess(it: List<Forecast>) {

                setResult(ForecastResponseState.Success(it))
           }

            override fun onNetworkIssue() {
                setResult(ForecastResponseState.NetworkFailure)
            }

            override fun onFailure() {
                setResult(ForecastResponseState.UnknownFailure)
            }

            override fun onEmpty() {
                setResult(ForecastResponseState.Empty)
            }

        })


    }

    fun setResult(state:ForecastResponseState){
        responseState.value = state
    }

}