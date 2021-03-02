package com.exam.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.exam.weather_forecast.data.database.dao.WeatherDAO
import com.exam.weather_forecast.data.domain.LocalWeatherDTO
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather
import com.exam.weather_forecast.data.services.IWeatherService
import java.io.IOException

class WeatherRepositoryImpl(private val weatherDAO: WeatherDAO, private val weatherService: IWeatherService): IWeatherRepository {

    override fun getWeathers(): LiveData<List<Weather>> {
        return Transformations.map(weatherDAO.getWeathers()) { list ->
            list.map {
                Weather(
                    it.id,
                    it.city,
                    it.status,
                    it.tempMin,
                    it.tempMax,
                    it.temp,
                    it.favorite
                )
            }
        }
    }

    override fun getWeather(id: Int): LiveData<Weather> {
        return Transformations.map(weatherDAO.getWeather(id)) { it ->
            Weather(
                it.id,
                it.city,
                it.status,
                it.tempMin,
                it.tempMax,
                it.temp,
                it.favorite
            )
        }
    }

    override suspend fun updateLocalWeathers(ids: List<Int>): Result<Unit> {
        return try {
            val result = weatherService.getWeathers(ids.joinToString(",")).let{ remoteWeather ->
                remoteWeather.weathers.map {
                    LocalWeatherDTO(
                        id = it.id,
                        city = it.city,
                        status = it.weather.let inner@{ weather ->
                            if(weather.isEmpty()){
                                return@inner "N/A"
                            }
                            return@inner weather[0].main
                        },
                        temp = it.main.temp,
                        tempMin = it.main.tempMin,
                        tempMax = it.main.tempMax,
                    )
                }
            }
            weatherDAO.insertWeathers(result)
            Result.Success(Unit)
        }catch(e: Exception) {
            Result.Error(IOException(e.message, e))
        }
    }

    override suspend fun setFavorite(id: Int, isFavorite: Boolean): Result<Unit> {
        return try {
            weatherDAO.setFavorite(id, isFavorite)
            Result.Success(Unit)
        }catch(e: Exception) {
            Result.Error(IOException(e.message, e))
        }
    }
}