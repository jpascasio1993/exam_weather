package com.exam.weather_forecast.utils.moshi

import com.squareup.moshi.JsonReader

inline fun JsonReader.readObject(body: () -> Unit) {
    beginObject()
    while(hasNext()){
        body()
    }
    endObject()
}

inline fun JsonReader.readArray(body: () -> Unit) {
    beginArray()
    while(hasNext()){
        body()
    }
    endArray()
}

inline fun <T:Any> JsonReader.readArrayToList(body: () -> T): List<T> {
    val items = mutableListOf<T>()
    beginArray()
    while(hasNext()){
        body().apply {
            items.add(this)
        }
    }
    endArray()
    return items.toList()
}

fun JsonReader.skip() {
    skipName()
    skipValue()
}




