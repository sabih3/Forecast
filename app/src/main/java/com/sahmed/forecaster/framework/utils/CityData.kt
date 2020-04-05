package com.sahmed.forecaster.framework.utils

import java.lang.IndexOutOfBoundsException

data class CityData(
    val id: Int?,
    var name: String){

    override fun hashCode(): Int {
        return this.id!!
    }

    override fun equals(other: Any?): Boolean {
        val otherObject = other as CityData
        try {
            return this.name.equals(other.name.trimStart(),ignoreCase = true)
        }catch (exc : IndexOutOfBoundsException){
            return false
        }


    }
}
