package com.sahmed.forecaster.framework.presentation.cities_weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sahmed.core.domain.CityWeathers
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.utils.UnitsFormatter
import kotlinx.android.synthetic.main.row_city_weather.view.*

class CityWeathersListAdapter : RecyclerView.Adapter<CityWeathersListAdapter.CityWeatherHolder>() {

    private var data = mutableListOf<CityWeathers>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherHolder {
        return CityWeatherHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_city_weather, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CityWeatherHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: MutableList<CityWeathers>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun removeAll(){
        val emptyList = mutableListOf<CityWeathers>()
        if(data.size >0 ) {
            data = emptyList
        }
        notifyDataSetChanged()
    }
    class CityWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item:CityWeathers) = with(itemView) {
            itemView.row_item_city_name.text = item.name
            itemView.row_item_temp.text =
                        "Min: " +item.main.temp_min.toString() + UnitsFormatter.getTemperatureUnit()+
                        " Max: "+item.main.temp_max.toString() + UnitsFormatter.getTemperatureUnit()

            itemView.row_item_wind.text = String.format("Wind: %.1f", (item.wind.speed!!*3.6))+" Km/h"

        }
    }
}