package com.sahmed.forecaster.framework

import android.app.Application
import com.sahmed.core.data.CityWeathersRepository
import com.sahmed.core.interactors.GetCityWeathers
import com.sahmed.forecaster.framework.network.RestClient

class ForecasterApplication :Application(){



    override fun onCreate() {
        super.onCreate()
        val weathersRepository = CityWeathersRepository(CityWeathersRepositoryImpl())

        val remoteDataSource = RestClient.getInstance()
        val cityWeatherRemoteRepository = CityWeatherRemoteRepository(remoteDataSource)
        ForecasterViewModelFactory.inject(this,
            Interactors(GetCityWeathers(weathersRepository)),cityWeatherRemoteRepository)

    }
}