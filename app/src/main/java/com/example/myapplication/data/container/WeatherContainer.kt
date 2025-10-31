package com.example.myapplication.data.container

import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.data.service.WeatherService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherContainer {

    companion object{
        private const val BASE_URL = "https://api.openweathermap.org"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val weatherService: WeatherService by lazy{
        retrofit.create(WeatherService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }
}