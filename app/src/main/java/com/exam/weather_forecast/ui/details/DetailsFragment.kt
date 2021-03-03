package com.exam.weather_forecast.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.exam.weather_forecast.R
import com.exam.weather_forecast.data.domain.Result
import com.exam.weather_forecast.data.domain.Weather
import com.exam.weather_forecast.databinding.DetailsFragmentBinding
import com.exam.weather_forecast.ui.WeatherFavoriteOnPressListener
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class DetailsFragment: Fragment() {

    val args: DetailsFragmentArgs by navArgs()

    val viewModel:DetailsViewModel by inject()

    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val swiper = requireView().findViewById<SwipeRefreshLayout>(R.id.details_swiper)
        swiper.apply {
            setOnRefreshListener {
                viewModel.getWeatherRemote(args.id)
            }
            viewModel.getWeatherRemote(args.id)
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner, {
            swiper.isRefreshing = it
        })

        viewModel.failOrSuccessWeather.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Error -> {
                    Snackbar.make(
                        requireView(),
                        "${it.exception.message}",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
                else -> {

                }
            }
        })

        viewModel.failOrSuccessFavorite.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Error -> {
                    Snackbar.make(
                        requireView(),
                        "${it.exception.message}",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
                else -> {}
            }
        })

        viewModel.getWeather(args.id).observe(viewLifecycleOwner, {
            binding.weather = it
            binding.favoriteOnPress = object : WeatherFavoriteOnPressListener {
                override fun onPress(view: View) {
                    viewModel.setFavorite(it.id, !it.favorite)
                }
            }
        })
    }
}