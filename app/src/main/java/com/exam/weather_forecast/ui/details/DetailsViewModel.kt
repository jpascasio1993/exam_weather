package com.exam.weather_forecast.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather
import com.exam.weather_forecast.data.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val weatherRepository: IWeatherRepository): ViewModel() {

    private val _failOrSuccessWeather = MutableLiveData<Result<Weather>>()
    val failOrSuccessWeather: LiveData<Result<Weather>> get() = _failOrSuccessWeather

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _failOrSuccessFavorite = MutableLiveData<Result<Unit>>()
    val failOrSuccessFavorite: LiveData<Result<Unit>> get() = _failOrSuccessFavorite

    fun getWeatherRemote(id: Int) = viewModelScope.launch {
        if(_isRefreshing.value == true) return@launch
        _isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherRemote(id).apply {
                viewModelScope.launch {
                    _failOrSuccessWeather.value = this@apply
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun getWeather(id: Int) = weatherRepository.getWeather(id)

    fun setFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        weatherRepository.setFavorite(id, isFavorite).apply {
           viewModelScope.launch {
               _failOrSuccessFavorite.value = this@apply
           }
        }
    }
}