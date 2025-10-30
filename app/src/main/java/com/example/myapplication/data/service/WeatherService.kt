package com.example.myapplication.data.service

import com.example.myapplication.data.dto.ResponseWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): Response<ResponseWeather>
}