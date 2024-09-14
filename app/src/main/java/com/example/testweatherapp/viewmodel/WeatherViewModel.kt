package com.example.testweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testweatherapp.repository.API_KEY
import com.example.testweatherapp.repository.WeatherRepository
import com.example.testweatherapp.server.ApiClient
import com.example.testweatherapp.server.ApiService
import retrofit2.create

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    constructor():this(WeatherRepository(ApiClient().getClient().create(ApiService::class.java)))

    fun loadCurrentWeather(lat: Double, lon: Double, unit: String) =
        repository.getCurrentWeather(lat, lon, unit)

    fun loadForecastWeather(lat: Double, lon: Double, unit: String) =
        repository.getForecastWeather(lat, lon, unit)

}