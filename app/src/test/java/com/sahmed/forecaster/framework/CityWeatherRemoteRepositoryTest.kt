package com.sahmed.forecaster.framework

import com.sahmed.core.domain.CityWeathers
import com.sahmed.core.domain.ResponseCityWeather
import com.sahmed.core.domain.Weather
import com.sahmed.forecaster.framework.network.APIs
import com.sahmed.forecaster.framework.network.repo.CityWeatherRemoteRepository
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.util.*

class CityWeatherRemoteRepositoryTest {

    @Mock
    lateinit var api: APIs

    @Mock
    lateinit var cityWeatherRepository: CityWeatherRemoteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cityWeatherRepository = Mockito.spy(
            CityWeatherRemoteRepository(
                api
            )
        )
    }

    @Test
    fun getWeatherOfCities(){
        var city_ids = "524901,703448,2643743"
        var call = Mockito.mock(Call::class.java)

        Mockito.doReturn(call).`when`(api).getWeatherOfCities(city_ids)
        api.getWeatherOfCities(city_ids)
        Mockito.verify(api,Mockito.times(1)).getWeatherOfCities(city_ids)
    }

    @Test
    fun handleResponse(){
        var response = Mockito.mock(Response::class.java)
        var responseCityWeather = Mockito.mock(ResponseCityWeather::class.java)
        val callback = Mockito.mock(CityWeatherRemoteRepository.CityWeatherCallback::class.java)

        var weatherList = Collections.singletonList(Mockito.mock(CityWeathers::class.java))

        Mockito.doReturn(true).`when`(response).isSuccessful
        Mockito.doReturn(responseCityWeather).`when`(response).body()
        Mockito.doReturn(weatherList).`when`(responseCityWeather).list

        cityWeatherRepository.handleResponse(response as Response<ResponseCityWeather>,callback)

        Mockito.verify(callback,Mockito.times(1)).onSuccess(weatherList)
    }

    @Test
    fun handleResponseEmpty(){

        var response = Mockito.mock(Response::class.java)
        var responseCityWeather = Mockito.mock(ResponseCityWeather::class.java)
        val callback = Mockito.mock(CityWeatherRemoteRepository.CityWeatherCallback::class.java)

        Mockito.doReturn(true).`when`(response).isSuccessful
        Mockito.doReturn(null).`when`(response).body()
        cityWeatherRepository.handleResponse(response as Response<ResponseCityWeather>,callback)
        Mockito.verify(callback,Mockito.times(1)).onEmpty()

    }

}