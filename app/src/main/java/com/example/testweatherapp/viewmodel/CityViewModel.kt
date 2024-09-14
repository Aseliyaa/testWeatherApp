package com.example.testweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testweatherapp.repository.CityRepository
import com.example.testweatherapp.server.ApiClient
import com.example.testweatherapp.server.ApiService
import retrofit2.create

class CityViewModel(val repository: CityRepository) : ViewModel() {
    constructor() : this(CityRepository(ApiClient().getClient().create(ApiService::class.java)))

    fun loadCity(q: String, limit: Int) =
        repository.getCities(q, limit)
}