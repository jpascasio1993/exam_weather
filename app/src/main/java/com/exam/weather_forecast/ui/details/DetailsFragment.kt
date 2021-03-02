package com.exam.weather_forecast.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exam.weather_forecast.R
import com.exam.weather_forecast.data.dummy.SampleData
import com.exam.weather_forecast.databinding.DetailsFragmentBinding
import com.exam.weather_forecast.ui.main.MainFragmentDirections

class DetailsFragment: Fragment() {

    val args: DetailsFragmentArgs by navArgs()
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
        Log.i("details", "onActivityCreated: ${args.id}")
        binding.weather = SampleData.weathers[0]
    }

}