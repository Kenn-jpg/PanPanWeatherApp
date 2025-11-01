package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.container.WeatherContainer
import com.example.myapplication.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val container = WeatherContainer()
    private val repository = container.weatherRepository

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: StateFlow<WeatherModel> = _weather.asStateFlow()

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val result = repository.getWeather(cityName)
                _weather.value = result
            } catch (e: Exception) {
                _weather.value = WeatherModel(
                    isError = true,
                    errorMessage = "HTTP 404 Not Found"
                )
            }
        }
    }

    fun getWeatherIconUrl(iconId: String): String {
        return repository.getWeatherIconUrl(iconId)
    }

}