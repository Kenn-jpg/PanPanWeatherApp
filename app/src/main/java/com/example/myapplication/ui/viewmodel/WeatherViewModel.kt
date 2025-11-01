package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.container.WeatherContainer
import com.example.myapplication.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherViewModel : ViewModel() {

//    private val container = WeatherContainer()
//    private val repository = container.weatherRepository

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: StateFlow<WeatherModel> = _weather

    private val _weatherIconUrl = MutableStateFlow<String?>(null)
    val weatherIconUrl: StateFlow<String?> = _weatherIconUrl

    val dateFormatted = weather.map{
        val date = Date(it.date * 1000L)
        SimpleDateFormat("MMMM dd", Locale.getDefault()).format(date)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val timeFormatted = weather.map{
        val date = Date(it.date * 1000L)
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunriseTime = weather.map{
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(it.sunrise * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunsetTime = weather.map{
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(it.sunset * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val weatherDetails = weather.map {
        listOf(
            Triple("HUMIDITY", "${it.humidity?: 0}%", R.drawable.humidity_icon),
            Triple("WIND", "${it.windSpeed?: 0}km/h", R.drawable.wind_icon),
            Triple("FEELS LIKE", "${it.temperature?: 0}°", R.drawable.feels_like_icon),
            Triple("RAIN FALL", "${it.rainVolume?: 0}mm", R.drawable.rain_fall_icon),
            Triple("PRESSURE", "${it.pressure?: 0}hPa", R.drawable.pressure_icon),
            Triple("CLOUDS", "${it.cloudiness?: 0}&", R.drawable.cloud_icon),
            )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun loadWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val container = WeatherContainer()
                val result = container.weatherRepository.getWeather(cityName)
                _weather.value = result.copy(
                    isError = false,
                    errorMessage = null
                )

                _weatherIconUrl.value = container.weatherRepository.getWeatherIconUrl(
                    result.weatherIcon
                )
            } catch (e: Exception) {
                _weather.value = _weather.value.copy(
                    isError = true,
                    errorMessage = "HTTP 404 Not Found"
                )
                _weatherIconUrl.value = null
            }
        }
    }
}