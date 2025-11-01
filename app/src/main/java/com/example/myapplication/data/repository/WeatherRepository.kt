package com.example.myapplication.data.repository

import com.example.myapplication.data.service.WeatherService
import com.example.myapplication.ui.model.WeatherModel

class WeatherRepository(private val service: WeatherService) {

    suspend fun getWeather(cityName: String): WeatherModel {
        val response = service.getCurrentWeather(
            cityName = cityName,
            units = "metric",
            apiKey = "384c015f690da62f99621121bb38a933"
        ).body()!!

        return WeatherModel(
            cityName = response.name,
            date = response.dt,

            weatherIcon = response.weather[0].icon,
            weatherDescription = response.weather[0].description,
            weatherTemperature = response.main.temp,

            humidity = response.main.humidity,
            windSpeed = response.wind.speed,
            temperature = response.main.temp,
            rainVolume = response.rain?.`1h`?: 0.0,
            pressure = response.main.pressure,
            cloudiness = response.clouds.all,

            sunrise = response.sys.sunrise,
            sunset = response.sys.sunset,

            isError = false,
            errorMessage = null
        )
    }

    fun getWeatherIconUrl(iconId: String): String {
        return "https://openweathermap.org/img/wn/$iconId@2x.png"
    }

}