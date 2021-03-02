package com.exam.weather_forecast.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.exam.weather_forecast.databinding.DetailsFragmentBinding
import com.exam.weather_forecast.ui.WeatherFavoriteOnPressListener
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
        // TODO: fix view on horizontal
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