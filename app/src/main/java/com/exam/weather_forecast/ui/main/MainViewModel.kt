package com.exam.weather_forecast.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.repository.IWeatherRepository
import kotlinx.coroutines.launch

class MainViewModel(private val weatherRepository: IWeatherRepository) : ViewModel() {

    private val _failOrSuccess = MutableLiveData<Result<Unit>>()
    val failOrSuccess: LiveData<Result<Unit>> get() = _failOrSuccess

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    fun getWeathers() = weatherRepository.getWeathers()

    fun setFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        weatherRepository.setFavorite(id, isFavorite)
    }

    fun updateLocalWeathers(ids: List<Int>) = viewModelScope.launch {
        if(_isRefreshing.value == true) return@launch
        _isRefreshing.value = true
        weatherRepository.updateLocalWeathers(ids).apply {
            _failOrSuccess.value = this
            _isRefreshing.value = false
        }
    }
}