package com.sahmed.forecaster.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

object ForecasterViewModelFactory:ViewModelProvider.Factory {

    private lateinit var application: Application
    private lateinit var dependencies: Interactors
    private lateinit var cityWeatherRemoteRepository: CityWeatherRemoteRepository

    fun inject(
        application: Application,
        interactors: Interactors,
        cityWeatherRepo: CityWeatherRemoteRepository
    ){
        ForecasterViewModelFactory.application = application
        ForecasterViewModelFactory.dependencies = interactors
        ForecasterViewModelFactory.cityWeatherRemoteRepository = cityWeatherRepo

    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(ForecasterViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java, Interactors::class.java,
                CityWeatherRemoteRepository::class.java)
                .newInstance(
                    application,
                    dependencies, cityWeatherRemoteRepository)
        } else {
            throw IllegalStateException("ViewModel must extend ForecasterViewModel")
        }
    }
}