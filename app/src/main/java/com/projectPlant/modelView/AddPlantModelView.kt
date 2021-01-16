package com.projectPlant.modelView

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectPlant.DAO.MyPlantDAO
import com.projectPlant.DAO.PlantDAO
import com.projectPlant.database.MyDatabase
import com.projectPlant.model.Plant
import com.projectPlant.model.PlantPersonSimple
import com.projectPlant.model.PlantSimple
import com.projectPlant.model.Storage
import com.projectPlant.service.ApiService
import kotlinx.coroutines.*
import retrofit2.await

class AddPlantModelView(
    plant: PlantSimple,
    myPersonPlant: PlantPersonSimple,
    application: Application,
    _context: Context
) : Storage(_context) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val plantDAO: PlantDAO = MyDatabase.getInstance(application).plantDAO
    private val myPlantDAO: MyPlantDAO = MyDatabase.getInstance(application).myPlantDAO

    private val _plantList = MutableLiveData<List<PlantSimple>>()
    val plantList: LiveData<List<PlantSimple>>
        get() = _plantList

    private val _simplePlant = MutableLiveData<PlantSimple>()
    val simplePlant: LiveData<PlantSimple>
        get() = _simplePlant

    private val _myPlant = MutableLiveData<PlantPersonSimple>()
    val myPlant: LiveData<PlantPersonSimple>
        get() = _myPlant

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    fun setPlanSimple(plant: PlantSimple) {
        _simplePlant.value = plant
    }

    init {
        _myPlant.value = myPersonPlant
        _simplePlant.value = plant
        uiScope.launch {
            initialize()
        }
    }

    private fun initialize() {
        uiScope.launch {
            getAllPlants("")
        }
    }

    private suspend fun getAllPlants(q: String = " ") {
        uiScope.launch {
            try {
                val response = ApiService.retrofitPlantService.getAllPlants(q, getToken()).await()
                _plantList.value = response
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    private suspend fun addMyPlant(plantPersonSimple: PlantPersonSimple) {
        uiScope.launch {
            ApiService.retrofitPlantService.addMyPlant(
                plant = plantPersonSimple,
                barrier = getToken()
            ).await()
        }
    }

    private suspend fun addPlant(plant: PlantSimple): Plant {
        return withContext(Dispatchers.IO) {
            val response =
                ApiService.retrofitPlantService.addPlant(plant = plant, barrier = getToken())
                    .await()
            response

        }
    }

    private suspend fun editMyPlant(plant: PlantPersonSimple) {
        uiScope.launch {
            ApiService.retrofitPlantService.editMyPlant(
                id = plant.id, plant = plant,
                barrier = getToken()
            ).await()
        }
    }

    private suspend fun deleteMyPlant(id: Int) {
        ApiService.retrofitPlantService.deleteMyPlant(id, getToken()).await()
    }

    private suspend fun waterMyPlant(id: Int, double: Double) {
        ApiService.retrofitPlantService.waterMyPlant(id, double, getToken()).await()
    }

    fun add(plant: PlantSimple, myPlantPersonSimple: PlantPersonSimple) {
        uiScope.launch {
            try {
                if (plant.id == 0) {
                    val reponse = addPlant(plant)
                    plantDAO.insert(plant)
                    myPlantPersonSimple.idPlant = reponse.id

                } else {
                    myPlantPersonSimple.idPlant = plant.id
                    myPlantDAO.insert(myPlantPersonSimple.getPlant())
                }
                addMyPlant(myPlantPersonSimple)

                _response.value = "Success: your plant was added"

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    fun edit(myPlantPersonSimple: PlantPersonSimple) {
        uiScope.launch {
            try {

                editMyPlant(myPlantPersonSimple)
                myPlantDAO.update(myPlantPersonSimple.getPlant())
                _response.value = "Success: your plant was edited"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    fun delete(id: Int?) {
        uiScope.launch {
            try {
                if (id != 0 && id != null) {
                    deleteMyPlant(id)
                    myPlantDAO.delete(id)
                }
                _response.value = "Success: your plant was deleted"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    fun water(id: Int, double: Double) {
        uiScope.launch {
            try {
                waterMyPlant(id, double)
                _response.value = "Success: your plant was watered"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}