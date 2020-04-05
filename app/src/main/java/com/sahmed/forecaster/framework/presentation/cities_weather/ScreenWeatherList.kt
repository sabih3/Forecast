package com.sahmed.forecaster.framework.presentation.cities_weather

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahmed.core.domain.CityWeathers

import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.ForecasterViewModelFactory
import com.sahmed.forecaster.framework.utils.CityData
import com.sahmed.forecaster.framework.utils.CityIDExtractor
import com.sahmed.forecaster.framework.utils.FileUtils
import kotlinx.android.synthetic.main.screen_weather_list.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ScreenWeatherList : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var adapter = CityWeathersListAdapter()
    var list = mutableListOf<CityWeathers>()
    lateinit var cityData : List<CityData>

    private lateinit var viewModel: CityWeathersViewModel

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

        return inflater.inflate(R.layout.screen_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this,ForecasterViewModelFactory).get(CityWeathersViewModel::class.java)
        initListView()
        initSearchFiled()

    }


    private fun ObserveSearchResults() {

        viewModel.observationState.observe(this, Observer {
            progress.visibility = View.GONE
            when(it){
                is CityWeathersViewModel.ResponseState.Loading->{
                    progress.visibility = View.VISIBLE
                }

                is CityWeathersViewModel.ResponseState.Empty ->{
                    progress.visibility = View.GONE
                    Toast.makeText(activity,getString(R.string.error_empty),Toast.LENGTH_LONG).show()
                }

                is CityWeathersViewModel.ResponseState.Success->{
                    list = it.response.toMutableList()
                    adapter.swapData(list)
                }

                is CityWeathersViewModel.ResponseState.NetworkFailure ->{
                    Toast.makeText(activity,getString(R.string.error_network),Toast.LENGTH_LONG).show()
                }

                is CityWeathersViewModel.ResponseState.UnknownFailure ->{
                    Toast.makeText(activity,getString(R.string.error_general),Toast.LENGTH_LONG).show()

                }
            }
        })
    }

    private fun initSearchFiled() {
        search_field.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1==EditorInfo.IME_ACTION_SEARCH){
                    performSearch()
                }
                return false
            }

        })
    }

    private fun performSearch() {
        var input_city_names = search_field.text.toString().trim().split(",")

        if(input_city_names.size>=3 && input_city_names.size<=7){
            list = mutableListOf()
            val cityIDs = CityIDExtractor.getCityIDs(activity!!, input_city_names)
            viewModel.getCityWeather(cityIDs)
            viewModel.observationState.observe(viewLifecycleOwner,Observer{ObserveSearchResults()})

        }else{
            search_field.setError(getString(R.string.validation_length_city_names))
        }
    }

    private fun initListView() {
        rv_city_weathers.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter = this@ScreenWeatherList.adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScreenWeatherList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
