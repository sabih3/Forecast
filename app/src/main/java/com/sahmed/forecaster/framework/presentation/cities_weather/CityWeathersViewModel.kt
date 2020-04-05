package com.sahmed.forecaster.framework.presentation.cities_weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sahmed.core.domain.CityWeathers
import com.sahmed.forecaster.framework.ForecastRemoteRepository
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository
import com.sahmed.forecaster.framework.ForecasterViewModel

class CityWeathersViewModel(application: Application,
                            cityWeatherRemoteRepository: CityWeatherRemoteRepository,
                            forecastRemoteRepository: ForecastRemoteRepository
):
    ForecasterViewModel(application,cityWeatherRemoteRepository,forecastRemoteRepository){

    private val dataState  = MutableLiveData<ResponseState>()
    val observationState :LiveData<ResponseState> = dataState

    sealed class ResponseState {
        object Loading: ResponseState()
        data class Success(val response : List<CityWeathers>) : ResponseState()
        object Empty : ResponseState()
        object UnknownFailure :ResponseState()
        object NetworkFailure :ResponseState()
    }



    fun getCityWeather(cityName: String){
        setResult(ResponseState.Loading)
        cityWeatherRemoteRepository.getWeatherOfCities(cityName,object:
            CityWeatherRemoteRepository.CityWeatherCallback{

            override fun onSuccess(weather: List<CityWeathers>) {

                setResult(ResponseState.Success(weather))
            }

            override fun onNetworkIssue() {
               setResult(ResponseState.NetworkFailure)
            }

            override fun onFailure() {
                setResult(ResponseState.UnknownFailure)
            }

            override fun onEmpty() {
                setResult(ResponseState.Empty)
            }

        })
    }

    fun setResult(state:ResponseState){
        dataState.value = state
    }
}