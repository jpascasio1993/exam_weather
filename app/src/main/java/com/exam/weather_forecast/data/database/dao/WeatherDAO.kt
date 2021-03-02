package com.exam.weather_forecast.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exam.weather_forecast.data.domain.LocalWeatherDTO

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeathers(weathers: List<LocalWeatherDTO>)

    @Query("UPDATE weathers SET fav=:isFavorite WHERE id =:id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * from weathers")
    fun getWeathers(): LiveData<List<LocalWeatherDTO>>

    @Query("SELECT * from weathers where id =:id")
    fun getWeather(id: Int): LiveData<LocalWeatherDTO>
}