package com.projectPlant.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.projectPlant.R
import com.projectPlant.databinding.FragmentWeatherBinding
import com.projectPlant.modelView.WeatherViewModel
import com.projectPlant.viewModelFactory.WeatherViewModelFactory

class WeatherFragment(var city: String = "Paris") : Fragment() {
    lateinit var viewModel: WeatherViewModel
    lateinit var binding: FragmentWeatherBinding

    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather, container, false
        )

        val parent = parentFragment as HomeFragment
        city = parent.city

        val viewModelFactory = WeatherViewModelFactory(city, requireContext())
        viewModel = ViewModelProvider(
            this.viewModelStore,
            viewModelFactory
        ).get(WeatherViewModel::class.java)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Glide.with(this).load(viewModel.weather.value?.icon).into(binding.tvWIcon)

        binding.calendarView.setOnDateChangeListener { _: CalendarView, i: Int, i1: Int, i2: Int ->
            val month = i1 + 1
            val monthS: String = if (month < 10) "0" + month else month.toString()
            val date = "$i-$monthS-$i2"
            getWeather(date)
        }

        viewModel.response.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(
                requireContext(),
                it, Toast.LENGTH_LONG
            )
        })

        return binding.root
    }

    private fun getWeather(date: String) {
        viewModel.dayWeather(date)
    }

    fun setWeather(
        date: String,
        weather: String,
        tempAv: String,
        tempMax: String,
        tempMin: String,
        humidity: String,
        wind: String
    ) {
        binding.tvW.text = date
        binding.tvWDescription.text = weather
        binding.tvWTMinV.text = tempMin
        binding.tvWTAvV.text = tempAv
        binding.tvWTMaxV.text = tempMax
        binding.tvWhAv.text = humidity
        binding.tvWWindV.text = wind
    }

}