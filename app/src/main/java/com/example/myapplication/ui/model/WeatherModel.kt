package com.example.myapplication.ui.model

data class WeatherModel(
    val cityName: String = "",
    val date: Int = 0,

    val weatherIcon: String = "",
    val weatherDescription: String = "",
    val weatherTemperature: Double = 0.0,

    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val temperature: Double = 0.0,
    val rainVolume: Double? = null,
    val pressure: Int = 0,
    val cloudiness: Int = 0,

    val sunrise: Int = 0,
    val sunset: Int = 0,

    val isError: Boolean = false,
    val errorMessage: String? = null,
)
