package com.exam.weather_forecast.di

import com.exam.weather_forecast.BuildConfig
import com.exam.weather_forecast.data.database.AppDatabase
import com.exam.weather_forecast.data.repository.IWeatherRepository
import com.exam.weather_forecast.data.repository.WeatherRepositoryImpl
import com.exam.weather_forecast.data.services.IWeatherService
import com.exam.weather_forecast.ui.details.DetailsViewModel
import com.exam.weather_forecast.ui.main.MainViewModel
import com.exam.weather_forecast.utils.okhttp.WeatherInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DIModules {
    val modules = module {

        single {
            AppDatabase(get())
        }

        single {
            get<AppDatabase>().weatherDAO()
        }

        single {
            OkHttpClient().newBuilder().addInterceptor(WeatherInterceptor()).build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_BASE_URL)
                .client(get())
                .addConverterFactory(
                    MoshiConverterFactory.create()
                )
                .build()
        }

        single<IWeatherService> {
            val retrofit = get<Retrofit>()
            retrofit.create(IWeatherService::class.java)
        }

        single<IWeatherRepository> {
            WeatherRepositoryImpl(get(), get())
        }

        viewModel {
            MainViewModel(get())
        }

        viewModel {
            DetailsViewModel(get())
        }
    }
}