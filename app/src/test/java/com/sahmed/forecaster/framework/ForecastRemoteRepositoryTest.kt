package com.sahmed.forecaster.framework

import com.sahmed.core.domain.forecast.Forecast
import com.sahmed.core.domain.forecast.ResponseForecast
import com.sahmed.forecaster.framework.network.APIs
import org.junit.Test
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ForecastRemoteRepositoryTest {

    @Mock
    lateinit var api : APIs

    @Mock
    lateinit var forecastRepo : ForecastRemoteRepository


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        forecastRepo = Mockito.spy(ForecastRemoteRepository(api))
    }

    @Test
    fun getForecast_test() {

        var lat = 25.45
        var lon = 35.50
        val callback = Mockito.mock(ForecastRemoteRepository.ForecastCallback::class.java)
        val call = Mockito.mock(Call::class.java)

        Mockito.doReturn(call).`when`(api).getForecast(lat.toString(),35.50.toString())
        forecastRepo.getForecast(lat,lon,callback)

        Mockito.verify(api,Mockito.times(1)).getForecast(lat.toString(),lon.toString())
    }

    @Test
    fun handleResponse(){
        var response = Mockito.mock(Response::class.java)
        var responseForecast = Mockito.mock(ResponseForecast::class.java)
        var callback = Mockito.mock(ForecastRemoteRepository.ForecastCallback::class.java)

        var mockedList = mutableListOf<Forecast>()//when tests are running without network, callback is returned with zero sized list
        Mockito.doReturn(true).`when`(response).isSuccessful
        Mockito.doReturn(responseForecast).`when`(response).body()
        Mockito.doReturn(mockedList).`when`(responseForecast).list

        forecastRepo.handleResponse(response as Response<ResponseForecast>,callback)

        Mockito.verify(callback,Mockito.times(1)).onSuccess(mockedList.toList())
    }

    @Test
    fun handleResponse_Empty(){
        var response = Mockito.mock(Response::class.java)
        var responseForecast = Mockito.mock(ResponseForecast::class.java)
        var callback = Mockito.mock(ForecastRemoteRepository.ForecastCallback::class.java)
        val mockedList = Collections.singletonList(Mockito.mock(Forecast::class.java))

        Mockito.doReturn(true).`when`(response).isSuccessful
        Mockito.doReturn(null).`when`(response).body()

        forecastRepo.handleResponse(response as Response<ResponseForecast>,callback)

        Mockito.verify(callback,Mockito.times(1)).onEmpty()
    }
}