package com.exam.weather_forecast

import android.app.Application
import com.exam.weather_forecast.di.DIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@WeatherApplication)
            modules(DIModules.modules)
        }
    }
}