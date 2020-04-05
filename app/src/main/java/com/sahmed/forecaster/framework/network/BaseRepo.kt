package com.sahmed.forecaster.framework.network

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.sahmed.core.domain.forecast.ResponseForecast
import com.squareup.moshi.Moshi

open class BaseRepo {

    fun handleErrorResponse(t: Throwable,callback: BaseCallback){
        if(t is UnknownHostException || t is SocketTimeoutException){
            //most probably Internet issue
            callback.onNetworkIssue()
        }else{
            callback.onFailure()
        }
    }
}