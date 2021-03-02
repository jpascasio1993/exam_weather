package com.exam.weather_forecast.data.domain

data class Weather(
    val id: Int,
    val city: String,
    val status: String,
    val tempMin: Float,
    val tempMax: Float,
    val temp: Float,
    val favorite: Boolean
)