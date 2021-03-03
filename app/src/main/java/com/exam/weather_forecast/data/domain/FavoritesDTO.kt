package com.exam.weather_forecast.data.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesDTO(
    @PrimaryKey(autoGenerate = false) val weatherId: Int
)