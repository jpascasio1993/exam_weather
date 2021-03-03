package com.exam.weather_forecast.data.domain

import androidx.room.*

@Entity(tableName = "weathers")
data class LocalWeatherDTO(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "temp_min") val tempMin: Float,
    @ColumnInfo(name = "temp_max") val tempMax: Float,
    @ColumnInfo(name = "temp") val temp: Float,
    @ColumnInfo(name = "fav", defaultValue = "false") val favorite: Boolean = false
)
