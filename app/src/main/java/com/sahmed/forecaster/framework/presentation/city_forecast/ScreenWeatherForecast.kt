package com.sahmed.forecaster.framework.presentation.city_forecast

import android.Manifest
import android.app.Instrumentation
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.sahmed.core.domain.forecast.Forecast

import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.ForecasterViewModelFactory
import kotlinx.android.synthetic.main.screen_weather_forecast.*


class ScreenWeatherForecast : Fragment() {
    private val REQUEST_CHECK_SETTINGS =  245
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
            initLocationProvider()
        }
    }


    fun initLocationProvider() {

        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        val client: SettingsClient = LocationServices.getSettingsClient(activity!!)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations){
                        fetchForecastData(location)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())

            fusedLocationClient.lastLocation.addOnSuccessListener {
                if(it!=null){
                    fetchForecastData(it)

                }

            }


        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(activity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    fun fetchForecastData(location: Location){
        viewModel.fetchForecastOfGeoLoc(location.latitude,location.longitude)

        viewModel.forecastData.observe(viewLifecycleOwner, Observer {
            when(it){

                is WeatherForecastViewModel.ForecastResponseState.Loading ->{
                    progressToShow(true)
                }

                is WeatherForecastViewModel.ForecastResponseState.Success ->{
                    fusedLocationClient.removeLocationUpdates(locationCallback)
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
                    initLocationProvider()
                } else {
                    checkLocationPermission()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CHECK_SETTINGS->{
                if(resultCode==-1){
                    initLocationProvider()
                }
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
