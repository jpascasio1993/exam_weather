package com.exam.weather_forecast.data.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteGroupWeatherDTO(@Json(name = "list") val weathers: List<WeatherDTO>) {

    @JsonClass(generateAdapter = true)
    data class WeatherDTO(
        val main: MainDTO,
        val weather: List<WeatherStatsDTO>,
        val id: Int,
        @Json(name = "name") val city: String
    ) {

        @JsonClass(generateAdapter = true)
        data class MainDTO(
            val temp: Float,
            @Json(name = "temp_min") val tempMin: Float,
            @Json(name = "temp_max") val tempMax: Float
        )

        @JsonClass(generateAdapter = true)
        data class WeatherStatsDTO(val main: String)
    }
}