package com.exam.weather_forecast.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_forecast.data.repository.IWeatherRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val weatherRepository: IWeatherRepository): ViewModel() {

    fun getWeather(id: Int) = weatherRepository.getWeather(id)

    fun setFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        weatherRepository.setFavorite(id, isFavorite)
    }
}