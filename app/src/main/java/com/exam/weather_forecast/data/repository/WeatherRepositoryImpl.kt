package com.exam.weather_forecast.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.exam.weather_forecast.data.database.dao.WeatherDAO
import com.exam.weather_forecast.data.domain.*
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

    override suspend fun getWeatherRemote(id: Int): Result<Weather> {
        return try {
            val weather = weatherService.getWeather(id).let {
                Weather(
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
                    favorite = weatherDAO.isFavorite(it.id)
                )
            }
            val localWeatherDTO = LocalWeatherDTO(
                id = weather.id,
                city = weather.city,
                status = weather.status,
                temp = weather.temp,
                tempMin = weather.tempMin,
                tempMax = weather.tempMax,
            )
            weatherDAO.insertWeathers(listOf(localWeatherDTO))
            Result.Success(weather)
        }catch(e: Exception) {
            Result.Error(IOException(e.message, e))
        }
    }

    override fun getWeather(id: Int): LiveData<Weather> {
        return Transformations.map(weatherDAO.getWeather(id)) {
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
            if(isFavorite){
                weatherDAO.setFavorite(FavoritesDTO(weatherId = id))
            } else {
                weatherDAO.removeFavorite(FavoritesDTO(weatherId = id))
            }
            Result.Success(Unit)
        }catch(e: Exception) {
            Result.Error(IOException(e.message, e))
        }
    }
}