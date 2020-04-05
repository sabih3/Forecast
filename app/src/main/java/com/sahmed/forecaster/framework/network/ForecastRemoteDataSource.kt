package com.sahmed.forecaster.framework.network

import com.sahmed.core.domain.forecast.ResponseForecast
import kotlinx.coroutines.CoroutineDispatcher

class ForecastRemoteDataSource(): BaseRepo() {

//    suspend fun getForecastByGeolocation(dispatcher: CoroutineDispatcher,lat:Double,lon:Double): Result<ResponseForecast> {
//        return safeApiCall(dispatcher) {RestClient.getInstance().getCoroutinedForecast(lat.toString(),lon.toString())}
//    }
}


