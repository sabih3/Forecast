package com.sahmed.forecaster.framework.presentation.city_forecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.*

import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.ForecastRemoteRepository
import com.sahmed.forecaster.framework.network.ForecastRemoteDataSource
import com.sahmed.forecaster.framework.network.RestClient
import kotlinx.coroutines.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ScreenWeatherForecast : Fragment() {
    private val PERMISSIONS_REQUEST_LOCATION = 244
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var weatherForecaseRemoteRepo:ForecastRemoteRepository
    lateinit var weatherforecastRemoteDatasource : ForecastRemoteDataSource

    var lat =MutableLiveData<Double>()
    var lon = MutableLiveData<Double>()
    lateinit var viewModel: WeatherForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        weatherforecastRemoteDatasource = ForecastRemoteDataSource()
        val remoteDataSources = RestClient.getInstance()
        weatherForecaseRemoteRepo = ForecastRemoteRepository(remoteDataSources)

        viewModel = WeatherForecastViewModel(activity!!.application,weatherForecaseRemoteRepo)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_weather_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLocationPermission()

    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.

                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION)

            }
        }else{
            fetchCurrentLocation()
        }
    }


    fun fetchCurrentLocation() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){

                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())

        fusedLocationClient.lastLocation.addOnSuccessListener {

            if(it!=null){
                viewModel.fetchForecastOfGeoLoc(it.latitude,it.longitude)

                observeForecastData()
            }

        }


    }
    private fun observeForecastData() {

        viewModel.forecastData.observe(this, Observer {
            it.size
        })




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSIONS_REQUEST_LOCATION ->{
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    fetchCurrentLocation()
                } else {
                    checkLocationPermission()
                }
                return
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScreenWeatherForecast().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
