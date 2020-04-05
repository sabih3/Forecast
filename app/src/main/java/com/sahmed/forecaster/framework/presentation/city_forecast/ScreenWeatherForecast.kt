package com.sahmed.forecaster.framework.presentation.city_forecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.sahmed.core.domain.forecast.Forecast

import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.ForecasterViewModelFactory
import kotlinx.android.synthetic.main.screen_weather_forecast.*


class ScreenWeatherForecast : Fragment() {
    private val PERMISSIONS_REQUEST_LOCATION = 244
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    var lat =MutableLiveData<Double>()
    var lon = MutableLiveData<Double>()
    lateinit var viewModel: WeatherForecastViewModel
    var adapter = ForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        viewModel = ViewModelProviders.of(this, ForecasterViewModelFactory).get(WeatherForecastViewModel::class.java)
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
        initListView()
        checkLocationPermission()

    }

    private fun initListView() {
        rv_forecast.apply {
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

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

                viewModel.forecastData.observe(viewLifecycleOwner, Observer {
                    when(it){

                        is WeatherForecastViewModel.ForecastResponseState.Loading ->{
                            progressToShow(true)
                        }

                        is WeatherForecastViewModel.ForecastResponseState.Success ->{
                            progressToShow(false)
                            setForecastList(it.response)
                        }

                        is WeatherForecastViewModel.ForecastResponseState.Empty ->{
                            progressToShow(false)
                            getToast(getString(R.string.error_empty)).show()

                        }

                        is WeatherForecastViewModel.ForecastResponseState.UnknownFailure ->{
                            progressToShow(false)
                            getToast(getString(R.string.error_general)).show()
                        }

                        is WeatherForecastViewModel.ForecastResponseState.NetworkFailure ->{
                            progressToShow(false)
                            getToast(getString(R.string.error_network)).show()
                        }
                    }

                })
            }

        }


    }
    private fun setForecastList(response: List<Forecast>) {
        adapter.swapData(response)
        rv_forecast.adapter = adapter
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


    fun progressToShow(toShow:Boolean){
        when(toShow){
            true -> progress.visibility = View.VISIBLE
            else -> progress.visibility = View.GONE
        }
    }

    fun getToast(message:String): Toast {
        return Toast.makeText(context,message,Toast.LENGTH_LONG)
    }
}
