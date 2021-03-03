package com.exam.weather_forecast.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.exam.weather_forecast.data.domain.FavoritesDTO
import com.exam.weather_forecast.data.domain.LocalWeatherDTO

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeathers(weathers: List<LocalWeatherDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavorite(favoriteWeather: FavoritesDTO)

    @Query("DELETE FROM favorites where weatherId =:id")
    suspend fun removeFavorite(id: Int)

    suspend fun removeFavorite(favoriteWeather: FavoritesDTO) = removeFavorite(favoriteWeather.weatherId)

    @Query("SELECT weathers.id, weathers.city, weathers.status, weathers.temp_min, weathers.temp_max, weathers.`temp`, (CASE WHEN favs.weatherId IS NULL THEN 0 ELSE 1 END) as fav from weathers left join (SELECT * from favorites) as favs on favs.weatherId = weathers.id")
    fun getWeathers(): LiveData<List<LocalWeatherDTO>>

    @Query("SELECT weathers.id, weathers.city, weathers.status, weathers.temp_min, weathers.temp_max, weathers.`temp`, (CASE WHEN favs.weatherId IS NULL THEN 0 ELSE 1 END) as fav from weathers left join (SELECT * from favorites) as favs on favs.weatherId = weathers.id where weathers.id =:id")
    fun getWeather(id: Int): LiveData<LocalWeatherDTO>

    @Query("SELECT (CASE WHEN favorites.weatherId IS NULL THEN 0 ELSE 1 END) as fav from favorites where weatherId =:id")
    fun isFavorite(id: Int): Boolean
}