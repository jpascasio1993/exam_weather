package com.exam.weather_forecast.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.exam.weather_forecast.R
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.dummy.SampleData
import com.exam.weather_forecast.data.extensions.tempColor
import com.exam.weather_forecast.ui.WeatherFavoriteOnPressListener
import com.exam.weather_forecast.weatherListItem
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by inject()

    interface WeatherListItemOnPressListener {
        fun onPress(view: View)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = requireView().findViewById<EpoxyRecyclerView>(R.id.recyclerView)
        val swiper = requireView().findViewById<SwipeRefreshLayout>(R.id.swipe)

        swiper.apply{
            setOnRefreshListener {
                viewModel.updateLocalWeathers(SampleData.cityIds)
            }
            viewModel.updateLocalWeathers(SampleData.cityIds)
        }

        viewModel.getWeathers().observe(viewLifecycleOwner, {
            recyclerView.withModels {
                it.forEachIndexed{ position, weather ->
                    weatherListItem {
                        id(position)
                        weather(weather)
                        color(weather.temp.tempColor(requireContext()))
                        itemOnPress(object: WeatherListItemOnPressListener{
                            override fun onPress(view: View) {
                                findNavController().navigate(MainFragmentDirections.actionNavHomeToNavDetails(weather.id))
                            }
                        })
                        favoriteOnPress(object : WeatherFavoriteOnPressListener {
                            override fun onPress(view: View) {
                                viewModel.setFavorite(weather.id, !weather.favorite)
                            }
                        })
                    }
                }
            }
        })

        viewModel.failOrSuccess.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Error -> {
                    Snackbar.make(requireView(), "${it.exception.message}", Snackbar.LENGTH_LONG)
                        .show()
                }
                else -> {
                }
            }
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner, {
            swiper.isRefreshing = it
        })
    }
}