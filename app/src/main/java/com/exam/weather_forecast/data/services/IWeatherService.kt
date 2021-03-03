package com.exam.weather_forecast.data.services

import com.exam.weather_forecast.data.domain.RemoteGroupWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {
    @GET("/data/2.5/group")
    suspend fun getWeathers(@Query("id") cityIds: String): RemoteGroupWeatherDTO

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("id") cityId: Int): RemoteGroupWeatherDTO.WeatherDTO
}