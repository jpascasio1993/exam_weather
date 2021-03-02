package com.exam.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather

interface IWeatherRepository {
    fun getWeathers(): LiveData<List<Weather>>
    fun getWeather(id: Int): LiveData<Weather>
    suspend fun updateLocalWeathers(ids: List<Int>): Result<Unit>
    suspend fun setFavorite(id: Int, isFavorite: Boolean): Result<Unit>
}