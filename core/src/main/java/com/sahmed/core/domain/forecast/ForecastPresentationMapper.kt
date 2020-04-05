package com.sahmed.core.domain.forecast

class ForecastPresentationMapper() : Mapper<List<Forecast>, List<Forecast>> {
    override fun mapFrom(type: List<Forecast>): List<Forecast> {
        val days = arrayListOf<String>()
        val mappedArray = arrayListOf<Forecast>()

        type.forEachIndexed { _, Forecast ->
            if (days.contains(Forecast.dt_txt?.substringBefore(" ")).not()) // Add day to days
                Forecast.dt_txt?.substringBefore(" ")?.let { days.add(it) }
        }

        days.forEach { day ->

            // Find min and max temp values each of the day
            val array = type.filter { it.dt_txt?.substringBefore(" ").equals(day) }

            val minTemp = array.minBy { it.main?.temp_min ?: 0.0 }?.main?.temp_min
            val maxTemp = array.maxBy { it.main?.temp_min ?: 0.0 }?.main?.temp_min

            array.forEach {
                it.main?.temp_min = minTemp // Set min
                it.main?.temp_max = maxTemp // Set max
                mappedArray.add(it) // add it to mappedArray
            }
        }

        return mappedArray
            .filter { it.dt_txt?.substringAfter(" ")?.substringBefore(":")?.toInt()!! >= 12 }
            .distinctBy { it.getDay() } // Eliminate same days
            .toList() // Return as list
    }
}