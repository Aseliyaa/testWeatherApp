package com.example.testweatherapp.repository

import com.example.testweatherapp.server.ApiService


class CityRepository(private val api: ApiService) {
    fun getCities(q:String, limit:Int) =
        api.getCitiesList(q, limit, API_KEY)

}