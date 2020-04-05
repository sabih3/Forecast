package com.sahmed.forecaster.framework

import com.sahmed.core.domain.forecast.Forecast
import com.sahmed.core.domain.forecast.ForecastPresentationMapper
import com.sahmed.core.domain.forecast.ResponseForecast
import com.sahmed.forecaster.framework.network.APIs
import com.sahmed.forecaster.framework.network.BaseCallback
import com.sahmed.forecaster.framework.network.RestClient
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ForecastRemoteRepository(val remoteDatSource:APIs) {


    fun getForecast(lat: Double, lon: Double, callback: ForecastCallback){

        val request = remoteDatSource.getForecast(lat.toString(), lon.toString())
        request.enqueue(object:Callback<ResponseForecast> {
            override fun onFailure(call: Call<ResponseForecast>, t: Throwable) {
                handleErrorResponse(t,callback)
            }

            override fun onResponse(call: Call<ResponseForecast>,
                                    response: Response<ResponseForecast>) {
                handleResponse(response,callback)
            }
        }
    )

    }

    fun handleResponse(
        response: Response<ResponseForecast>, callback: ForecastCallback) {
        if(response.isSuccessful){
            if(!response.body()?.list!!.isEmpty()){
                val mappedList = response.body()!!.list.map {
                    ForecastPresentationMapper()
                }.toList()

                callback.onSuccess(response.body()!!.list)
            }else{
                callback.onEmpty()
            }
        }
    }

    fun handleErrorResponse(t: Throwable, callback: ForecastCallback) {
        if(t is UnknownHostException || t is SocketTimeoutException){
            //most probably Internet issue
            callback.onNetworkIssue()
        }else{
            callback.onFailure()
        }
    }


    interface ForecastCallback:BaseCallback{
        fun onSuccess(it: List<Forecast>)
    }
}