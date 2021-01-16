package com.projectPlant.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projectPlant.modelView.WeatherViewModel

class WeatherViewModelFactory(
    var city: String = "",
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(_city = city, _context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}