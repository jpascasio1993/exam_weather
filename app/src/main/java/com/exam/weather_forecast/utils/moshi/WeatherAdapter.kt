//package com.exam.weather_forecast.utils.moshi
//
//import com.exam.weather_forecast.data.domain.WeatherDTO
//import com.squareup.moshi.JsonAdapter
//import com.squareup.moshi.JsonReader
//import com.squareup.moshi.JsonWriter
//
//class WeatherAdapter: JsonAdapter<List<WeatherDTO>>() {
//
//    companion object {
//        val NAMES = JsonReader.Options.of("main", "weather", "id", "name")
//    }
//
//    override fun fromJson(reader: JsonReader): List<WeatherDTO> {
//        val weathers = listOf<WeatherDTO>()
//        var id: Int
//        var weatherStatus: String
//        var temp: Float
//        var tempMin: Float
//        var tempMax: Float
//        var city: String
//        reader.readObject {
//            when(reader.nextName()) {
//                "list" -> {
//                    reader.readArray {
//                        reader.readObject {
//                            when(reader.selectName(NAMES)) {
//                                0 -> {
//                                    reader.readObject {
//
//                                    }
//                                }
//                                else -> reader.skip()
//                            }
//                        }
//                    }
//                } else -> reader.skip()
//            }
//        }
//        return weathers
//    }
//
//    override fun toJson(writer: JsonWriter, value: List<WeatherDTO>?) {
//        TODO("Not yet implemented")
//    }
//
//}