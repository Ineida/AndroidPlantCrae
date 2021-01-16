package com.projectPlant.modelView

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectPlant.model.Storage
import com.projectPlant.model.Weather
import com.projectPlant.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(
    var _city: String = "",
    _context: Context
) : Storage(_context) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    init {
        Log.i("WeatherViewModel", "created")
        uiScope.launch {
            getWeather(_city, SimpleDateFormat("yyyy-mm-dd").format(Date()))
        }
    }

    suspend fun getWeather(city: String, date: String) {
        uiScope.launch {
            try {
                val response =
                    ApiService.retrofitWeatherService.getWeather(city, date, getToken()).await()
                _weather.value = response
            } catch (e: Exception) {
                _response.value = "Failure: can't get the weather"
            }
        }
    }

    fun dayWeather(date: String) {
        uiScope.launch {
            try {
                getWeather(_city, date)
            } catch (e: Exception) {
                _response.value = "Failure: can't get the weather"
            }
        }
    }
}