package com.sahmed.forecaster.framework.presentation.city_forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sahmed.core.domain.forecast.Forecast
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.utils.UnitsFormatter
import kotlinx.android.synthetic.main.row_forecast.view.*
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

    private var data: List<Forecast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_forecast, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<Forecast>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Forecast) = with(itemView) {
            itemView.row_item_dateTime.text = item.getDay()
            itemView.row_item_forecast_temp.text = "Temp: "+item.main.temp_max.toString()+UnitsFormatter.getTemperatureUnit()
            itemView.row_item_feels_like.text = "Feels like: "+item.main.feels_like.toString()+UnitsFormatter.getTemperatureUnit()
            try{
                itemView.row_item_desc.text = item.weather.first().description.capitalize()
            }catch (exc:Exception){

            }


        }
    }
}