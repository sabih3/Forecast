package com.sahmed.forecaster.framework

import android.app.Application
import com.sahmed.forecaster.framework.network.RestClient
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository

class ForecasterApplication :Application(){



    override fun onCreate() {
        super.onCreate()
        val remoteDataSource = RestClient.getInstance()

        val cityWeatherRemoteRepository =
            CityWeatherRemoteRepository(
                remoteDataSource
            )

        val forecastRemoteRepository =
            ForecastRemoteRepository(
                remoteDataSource
            )

        ForecasterViewModelFactory.inject(this,
            cityWeatherRemoteRepository,forecastRemoteRepository)

    }
}