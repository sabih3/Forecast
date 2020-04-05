package com.sahmed.forecaster.framework.utils

import com.sahmed.forecaster.framework.network.Constants

object UnitsFormatter {

    fun getTemperatureUnit():String{

        when(Constants.CONFIGURED_UNITS_FORMAT){

            Constants.UNIT_METRIC -> return " \u2103"

            Constants.UNIT_IMPERIAL -> return " \u2109"
        }

        return ""
    }
}