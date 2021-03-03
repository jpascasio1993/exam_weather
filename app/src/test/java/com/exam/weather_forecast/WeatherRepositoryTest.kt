package com.exam.weather_forecast

import com.exam.weather_forecast.data.database.dao.WeatherDAO
import com.exam.weather_forecast.data.domain.RemoteGroupWeatherDTO
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather
import com.exam.weather_forecast.data.repository.IWeatherRepository
import com.exam.weather_forecast.data.repository.WeatherRepositoryImpl
import com.exam.weather_forecast.data.services.IWeatherService
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private lateinit var dao: WeatherDAO
    private lateinit var weatherService: IWeatherService
    private lateinit var sampleWeathers: List<Weather>
    private lateinit var repository: IWeatherRepository

    @Before
    fun setUpMocks() {
        dao = mockk(relaxed = true)
        weatherService = mockk()
        sampleWeathers = listOf(
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
        repository = WeatherRepositoryImpl(dao, weatherService)
    }

    @Test
    fun `get weather from remote`() = runBlockingTest  {

        val weather = sampleWeathers[0]
        val remoteDTO: RemoteGroupWeatherDTO.WeatherDTO = mockk(relaxed = true)
        val cityId = weather.id

        coEvery { weatherService.getWeather(cityId) } returns remoteDTO

        val result = repository.getWeatherRemote(cityId)

        coVerify {
             repository.getWeatherRemote(cityId)
        }

        assert(result is Result.Success<*>) { "Should return Result<Weather>" }
    }

    @Test
    fun `retrieve weathers from remote and update local`() = runBlockingTest {
        val remoteDTO: RemoteGroupWeatherDTO = mockk(relaxed = true)
        val cityIds = sampleWeathers.map { it.id }
        val joinedIds = cityIds.joinToString(",")
        coEvery { weatherService.getWeathers(joinedIds) } returns remoteDTO

        val result = repository.updateLocalWeathers(cityIds)

        coVerify {
            repository.updateLocalWeathers(cityIds)
        }

        assert(result is Result.Success<*>) { "Should return Result<Unit>" }
    }

    @Test
    fun `should throw exception upon remote service disruption`() = runBlockingTest {
        val weather = sampleWeathers[0]
        val cityId = weather.id

        val exception = IOException("Unable to resolve host")

        coEvery { weatherService.getWeather(cityId) } throws exception

        val result = repository.getWeatherRemote(cityId)

        coVerify {
            repository.getWeatherRemote(cityId)
        }

        assert(result is Result.Error) { "returns Result.Error with an exception" }
    }

    @After
    fun clearMock() {
        clearAllMocks()
    }

}