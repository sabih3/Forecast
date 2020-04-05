package com.sahmed.forecaster.framework.presentation.cities_weather

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather
import com.sahmed.forecaster.framework.ForecastRemoteRepository
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository
import com.sahmed.forecaster.framework.presentation.cities_weather.CityWeathersViewModel.ResponseState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class CityWeathersViewModelTest{

    private lateinit var cityWeathersModel: CityWeathersViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var application:Application

    @MockK
    lateinit var cityWeatherRemoteRepository:CityWeatherRemoteRepository

    @MockK
    lateinit var forecastRemoteRepository: ForecastRemoteRepository

    @MockK
    lateinit var viewStateObserver: Observer<ResponseState>

    @Before
    fun setup(){

        MockKAnnotations.init(this)
        cityWeathersModel = CityWeathersViewModel(application,
                            cityWeatherRemoteRepository,forecastRemoteRepository)


    }

    @Test
    fun check(){

        cityWeathersModel.observationState.observeForever(viewStateObserver)
        cityWeathersModel.setResult(ResponseState.Loading)
        verify(viewStateObserver).onChanged(ResponseState.Loading)





    }
}