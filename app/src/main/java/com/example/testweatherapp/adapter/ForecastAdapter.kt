package com.example.testweatherapp.adapter

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.testweatherapp.databinding.ForecastViewholderBinding
import com.example.testweatherapp.model.ForecastResponseApi
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private lateinit var binding: ForecastViewholderBinding

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        binding = ForecastViewholderBinding.inflate(inflate, parent, false)
        return ViewHolder()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "DiscouragedApi")
    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val binding = ForecastViewholderBinding.bind(holder.itemView)
        val data =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(differ.currentList[position].dtTxt.toString())
        val calendar = Calendar.getInstance()
        calendar.time = data

        val dayOfWeekName = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tue"
            4 -> "Wen"
            5 -> "Thu"
            6 -> "Fri"
            7 -> "Sat"
            else -> "-"
        }
        binding.nameDayTxt.text = dayOfWeekName
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val amPm = if(hour < 12) "am" else "pm"
        val hour12 = calendar.get(Calendar.HOUR)
        binding.hourTxt.text = hour12.toString() + amPm
        binding.tempTxt.text = differ.currentList[position].main?.temp?.roundToInt().toString() + "°"

        val icon = when(differ.currentList[position].weather?.get(0)?.icon.toString()){
            "01d", "01h" -> "sunny"
            "02d", "02h" -> "cloudy_sunny"
            "03d", "03h" -> "cloudy_sunny"
            "04d", "04h" -> "cloudy"
            "09d", "09h" -> "rainy"
            "10d", "10h" -> "rainy"
            "11d", "11h" -> "storm"
            "13d", "13h" -> "snowy"
            "50d", "50h" -> "windy"
            else -> "sunny"
        }

        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            icon,
            "drawable", binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.pic)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<ForecastResponseApi.Data>() {
        override fun areItemsTheSame(
            oldItem: ForecastResponseApi.Data,
            newItem: ForecastResponseApi.Data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ForecastResponseApi.Data,
            newItem: ForecastResponseApi.Data
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}