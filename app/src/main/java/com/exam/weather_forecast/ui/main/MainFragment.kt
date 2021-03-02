package com.exam.weather_forecast.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.exam.weather_forecast.R
import com.exam.weather_forecast.data.dummy.SampleData
import com.exam.weather_forecast.data.tempColor
import com.exam.weather_forecast.weatherListItem

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val recyclerView = requireView().findViewById<EpoxyRecyclerView>(R.id.recyclerView)
        val sampleData = SampleData.weathers
        recyclerView.withModels {
            sampleData.forEachIndexed{ position, weather ->
                weatherListItem {
                    id(position)
                    weather(weather)
                    color(weather.temp.tempColor(requireContext()))
                    itemOnPress(object: WeatherListItemOnPressListener{
                        override fun onPress(view: View) {
                            findNavController().navigate(MainFragmentDirections.actionNavHomeToNavDetails(1))
                        }
                    })
                    favoriteOnPress(object : WeatherFavoriteOnPressListener {
                        override fun onPress(view: View) {
                            Log.i("favorite", "onPress: ")
                        }
                    })
                }
            }
        }
    }

    interface WeatherListItemOnPressListener {
        fun onPress(view: View)
    }

    interface WeatherFavoriteOnPressListener {
        fun onPress(view: View)
    }
}