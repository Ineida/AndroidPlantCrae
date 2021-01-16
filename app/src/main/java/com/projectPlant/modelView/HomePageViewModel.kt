package com.projectPlant.modelView

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectPlant.DAO.MyPlantDAO
import com.projectPlant.DAO.PlantDAO
import com.projectPlant.database.MyDatabase
import com.projectPlant.model.Person
import com.projectPlant.model.Plant
import com.projectPlant.model.Storage
import com.projectPlant.model.Weather
import com.projectPlant.service.ApiService
import kotlinx.coroutines.*
import retrofit2.await

class HomePageViewModel(
    id: Int,
    application: Application,
    _context: Context
) : Storage(_context) {
    private val idPerson: Int = id
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val plantDAO: PlantDAO = MyDatabase.getInstance(application).plantDAO
    private val myPlantDAO: MyPlantDAO = MyDatabase.getInstance(application).myPlantDAO

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _plantList = MutableLiveData<List<Plant>>()
    val plantList: LiveData<List<Plant>>
        get() = _plantList

    private val _historique = MutableLiveData<List<Plant>>()
    private val historique: LiveData<List<Plant>>
        get() = _historique

    init {
        Log.i("PlantListViewModel", "created")
        initialize()
    }

    private fun initialize() {
        getHistorique()
        uiScope.launch {
            getPerson(idPerson)
            val plants = getMyPlants(idPerson)
            if (plants == null) {
                _plantList.value = historique.value
            } else {
                _plantList.value = plants
            }
        }
    }

    private fun getHistorique() {
        try {
            val result: List<Plant> = myPlantDAO.get(idPerson)
            for (plant in result) {
                plant.plantByIdplant = plantDAO.get(plant.id)
            }
            _historique.value = result

        } catch (e: Exception) {
            _response.value = "Failure: ${e.message}"
        }
    }

    private suspend fun getMyPlants(idPerson: Int): List<Plant>? {
        try {
            return withContext(Dispatchers.IO) {
                val response =
                    ApiService.retrofitPlantService.getPlants(idPerson, getToken()).await()
                response
                response
            }
        } catch (e: java.lang.Exception) {
            return null
        }
    }

    private suspend fun getPerson(idPerson: Int) {
        uiScope.launch {
            try {
                val response =
                    ApiService.retrofitPersonService.getPerson(idPerson, getToken()).await()
                _person.value = response
            } catch (e: Exception) {
                _response.value = "Failure: can't gat your personal information"
            }
        }
    }

    fun getWeather() {
        uiScope.launch {
            try {
                val response =
                    ApiService.retrofitWeatherService.getWeather(person.value?.city, getToken())
                        .await()
                _weather.value = response
            } catch (e: Exception) {
                _response.value = "Failure: can't get weather"
            }
        }
    }

    fun setWeather(weather: Weather) {
        _weather.value = weather
    }

}