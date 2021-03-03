package com.exam.weather_forecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exam.weather_forecast.data.database.dao.WeatherDAO
import com.exam.weather_forecast.data.domain.FavoritesDTO
import com.exam.weather_forecast.data.domain.LocalWeatherDTO

@Database(
    entities = [LocalWeatherDTO::class, FavoritesDTO::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO

    companion object {
        @Volatile private var instance: AppDatabase ? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: build(context).also { instance = it }
        }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "weather.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}