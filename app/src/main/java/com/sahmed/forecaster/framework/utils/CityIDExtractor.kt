package com.sahmed.forecaster.framework.utils

import android.content.Context

class CityIDExtractor {

    companion object{
        fun getCityIDs(context: Context,
                       cityNames :List<String>):String{
            var cityData : List<CityData> = FileUtils.getCityList(context)!!.city_data
            var matching = mutableListOf<CityData>()

            cityNames.forEach {
                var cityObject = CityData(0,it)
                matching.add(cityObject)
            }

            var commas  =""

            val filteredList = cityData.filter {
                matching.contains(it)
            }.distinctBy {
                it.name
            }

            filteredList.forEach {
                commas+= it.id.toString()+","
            }

            return commas
        }
    }

}