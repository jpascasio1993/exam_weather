package com.exam.weather_forecast

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.exam.weather_forecast.data.database.AppDatabase
import com.exam.weather_forecast.data.database.dao.WeatherDAO
import com.exam.weather_forecast.data.domain.LocalWeatherDTO
import com.exam.weather_forecast.data.domain.RemoteGroupWeatherDTO
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather
import com.exam.weather_forecast.data.repository.IWeatherRepository
import com.exam.weather_forecast.data.repository.WeatherRepositoryImpl
import com.exam.weather_forecast.data.services.IWeatherService
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: WeatherDAO
    private lateinit var weatherService: IWeatherService
    private lateinit var sampleWeathers: List<Weather>
    private lateinit var repository: IWeatherRepository
    @Before
    fun setUpMocks() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = appDatabase.weatherDAO()
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
    fun `getweather from remote`() = runBlockingTest(Dispatchers.Unconfined)  {

        val weather = sampleWeathers[0]
        val localWeatherDTO = LocalWeatherDTO(
            weather.id,
            weather.city,
            weather.status,
            weather.tempMin,
            weather.tempMax,
            weather.temp,
            weather.favorite
        )
        val remoteDTO: RemoteGroupWeatherDTO.WeatherDTO = mockk(relaxed = true)
        val cityId = weather.id

        coEvery { weatherService.getWeather(cityId) } returns remoteDTO
        coEvery { dao.isFavorite(cityId) } returns true
        val result = repository.getWeatherRemote(cityId)
        coVerify {
             repository.getWeatherRemote(cityId)
//            dao.insertWeathers(listOf(localWeatherDTO))
        }
        assert(result is Result.Success<*>) { "should produce Success type Result $result" }
    }
}