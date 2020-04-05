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
import com.sahmed.forecaster.framework.utils.CityIDExtractor
import kotlinx.android.synthetic.main.screen_weather_list.*
import kotlinx.android.synthetic.main.screen_weather_list.progress

class ScreenCityWeathersList : Fragment() {
    var adapter = CityWeathersListAdapter()
    var list = mutableListOf<CityWeathers>()

    private lateinit var viewModel: CityWeathersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            when(it){
                is CityWeathersViewModel.ResponseState.Loading->{
                    progressToShow(true)
                }

                is CityWeathersViewModel.ResponseState.Empty ->{
                    progressToShow(false)
                    getToast(getString(R.string.error_empty))
                }

                is CityWeathersViewModel.ResponseState.Success->{
                    progressToShow(false)
                    setDataInList(it)

                }

                is CityWeathersViewModel.ResponseState.NetworkFailure ->{
                    progressToShow(false)
                    getToast(getString(R.string.error_network))
                }

                is CityWeathersViewModel.ResponseState.UnknownFailure ->{
                    progressToShow(false)
                    getToast(getString(R.string.error_general))


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
            progressToShow(true)
            list = mutableListOf()
            val cityIDs = CityIDExtractor.getCityIDs(activity!!, input_city_names)
            if(cityIDs.isEmpty()){
                progressToShow(false)
                search_field.setError(getString(R.string.validation_city_names_invalid))
            }else{
                viewModel.getCityWeather(cityIDs)
                viewModel.observationState.observe(viewLifecycleOwner,Observer{ObserveSearchResults()})
            }


        }else{
            progressToShow(false)
            search_field.setError(getString(R.string.validation_length_city_names))
        }
    }

    private fun initListView() {
        rv_city_weathers.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter = this@ScreenCityWeathersList.adapter
        }
    }

    private fun setDataInList(it: CityWeathersViewModel.ResponseState.Success) {
        list = it.response.toMutableList()
        adapter.swapData(list)
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
