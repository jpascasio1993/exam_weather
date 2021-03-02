package com.exam.weather_forecast.data.dummy

import com.exam.weather_forecast.data.domain.Weather

object SampleData {
    val weathers = listOf<Weather>(
        Weather(
            123,
            "City A",
            "Rainy",
            35f,
            36f,
            35f,
            false
        ),
        Weather(
            124,
            "City B",
            "Fog",
            35f,
            36f,
            35f,
            true
        ),
        Weather(
            125,
            "City C",
            "Cloudy",
            35f,
            36f,
            35f,
            false
        )
    )
}