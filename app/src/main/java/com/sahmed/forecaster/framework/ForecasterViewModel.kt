package com.sahmed.forecaster.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sahmed.forecaster.framework.ForecasterApplication
import com.sahmed.forecaster.framework.Interactors
import com.sahmed.forecaster.framework.network.APIs

open class ForecasterViewModel(application: Application,val interactors: Interactors,
                                val cityWeatherRemoteRepository: CityWeatherRemoteRepository):
    AndroidViewModel(application) {

}