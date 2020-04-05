package com.sahmed.forecaster.framework.network

interface BaseCallback {

    fun onNetworkIssue()
    fun onFailure()
    fun onEmpty()
}