package com.sahmed.forecaster.framework.utils

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileUtilsTest {

    lateinit var appcontext : Context
    @Before
    fun setup(){
        appcontext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getCityList() {

        val cityDataObject = FileUtils.getCityList(appcontext)
        assertNotNull(cityDataObject)
    }

    @Test
    fun checkFileReaderIsReturningCorrectCityData(){

        val cityDataObject = FileUtils.getCityList(appcontext)
        assertEquals(cityDataObject!!.city_data.get(0).id,833)
        assertEquals(cityDataObject!!.city_data.get(0).name,"Ḩeşār-e Sefīd")

        assertEquals(cityDataObject!!.city_data.get(1).id,2960)
        assertEquals(cityDataObject!!.city_data.get(1).name,"‘Ayn Ḩalāqīm")

    }
}