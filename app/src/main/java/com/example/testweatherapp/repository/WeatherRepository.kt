package com.example.testweatherapp.repository

import com.example.testweatherapp.server.ApiService

const val API_KEY = "0ca5a1fcb26db8441de3fa602536d555"
class WeatherRepository(private val api: ApiService) {
    fun getCurrentWeather(lat: Double, lon: Double, unit: String) =
        api.getCurrentWeather(lat, lon, unit, API_KEY)

    fun getForecastWeather(lat: Double, lon: Double, unit: String) =
        api.getForecastWeather(lat, lon, unit, API_KEY)
}