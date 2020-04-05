package com.sahmed.forecaster.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository

open class ForecasterViewModel(application: Application,
                               val cityWeatherRemoteRepository: CityWeatherRemoteRepository,
                               val forecastRemoteRepository: ForecastRemoteRepository):
    AndroidViewModel(application) {

}