package com.sahmed.forecaster.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository
import java.lang.IllegalStateException

object ForecasterViewModelFactory:ViewModelProvider.Factory {

    private lateinit var application: Application
    private lateinit var cityWeatherRemoteRepository: CityWeatherRemoteRepository
    private lateinit var forecastRepository: ForecastRemoteRepository

    fun inject(
        application: Application,
        cityWeatherRepo: CityWeatherRemoteRepository,
        forecastRemoteRepository: ForecastRemoteRepository
    ){
        ForecasterViewModelFactory.application = application
        ForecasterViewModelFactory.cityWeatherRemoteRepository = cityWeatherRepo
        ForecasterViewModelFactory.forecastRepository = forecastRemoteRepository

    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(ForecasterViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java,
                CityWeatherRemoteRepository::class.java,ForecastRemoteRepository::class.java)
                .newInstance(
                    application, cityWeatherRemoteRepository,forecastRepository)
        } else {
            throw IllegalStateException("ViewModel must extend ForecasterViewModel")
        }
    }
}