package com.example.myapplication.ui.model

data class WeatherModel(
    val cityName: String,
    val date: String,

    val description: String,
    val iconUrl: String,

    val humidity: Int,
    val windSpeed: Double,
    val temperature: Double,
    val rainVolume: Double,
    val pressure: Int,
    val cloudiness: Int,

    val sunrise: Long,
    val sunset: Long
)
