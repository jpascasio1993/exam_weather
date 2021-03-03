package com.exam.weather_forecast.data.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.exam.weather_forecast.R

fun Float.tempColor(context: Context): Int {
    return when(this){
        in Float.NEGATIVE_INFINITY..0F -> ContextCompat.getColor(context, R.color.freezing)
        in 0F..15F -> ContextCompat.getColor(context, R.color.cold)
        in 15F..30F -> ContextCompat.getColor(context, R.color.warm)
        else -> ContextCompat.getColor(context, R.color.hot)
    }
}