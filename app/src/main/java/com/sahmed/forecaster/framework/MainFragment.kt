package com.sahmed.forecaster.framework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.presentation.cities_weather.ScreenWeatherList
import com.sahmed.forecaster.framework.presentation.city_forecast.ScreenWeatherForecast
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cities_weather.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_screenWeatherList)

        }

        btn_current_forecast.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_screenWeatherForecast)

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }


    }
}
