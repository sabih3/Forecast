package com.sahmed.forecaster.framework.presentation.cities_weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.Weather
import com.sahmed.forecaster.framework.CityWeatherRemoteRepository
import com.sahmed.forecaster.framework.ForecasterViewModel
import com.sahmed.forecaster.framework.Interactors

class CityWeathersViewModel(application: Application,
                            interactors: Interactors,
                            cityWeatherRemoteRepository: CityWeatherRemoteRepository):
    ForecasterViewModel(application,interactors,cityWeatherRemoteRepository){

    private val dataState  = MutableLiveData<ResponseState>()
    val observationState = dataState

    sealed class ResponseState {
        object Loading: ResponseState()
        data class Success(val response : List<CityWeathers>) : ResponseState()
        object Empty : ResponseState()
        object UnknownFailure :ResponseState()
        object NetworkFailure :ResponseState()
    }

    fun getCityWeather(cityName: String){
        setResult(ResponseState.Loading)
        cityWeatherRemoteRepository.getWeatherOfCities(cityName,object:CityWeatherRemoteRepository.CityWeatherCallback{

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