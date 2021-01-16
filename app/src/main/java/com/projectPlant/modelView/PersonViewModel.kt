package com.projectPlant.modelView

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectPlant.model.Person
import com.projectPlant.model.PersonRequest
import com.projectPlant.model.Storage
import com.projectPlant.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*

class PersonViewModel(
    _id: Int,
    _username: String = "",
    _context: Context
) : Storage(context = _context) {
    private val id: Int = _id
    private val username: String = _username
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person


    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private fun initializePerson() {
        uiScope.launch {
            if (id != 0) {
                getPersonalInformation(id)
            } else {
                _person.value = Person(username = username)
            }
        }
    }

    init {
        Log.i(
            "Inscription" +
                    "ViewModel", "created"
        )
        initializePerson()
    }

    @SuppressLint("SimpleDateFormat")
    fun setBirthDate(date: Date) {
        _person.value?.birthdate = SimpleDateFormat("yyyy-mm-dd").format(date)
    }

    fun addPersonalInformation(person: Person) {
        uiScope.launch {
            try {
                val personR = personRequest(person)
                val response =
                    ApiService.retrofitPersonService.addPersonalInformation(personR, getToken())
                        .await()

                _person.value = response
            } catch (e: Exception) {
                _response.value = "Failure: can't add your personal information"
            }
        }
    }

    private fun personRequest(person: Person) = PersonRequest(
        person.name, person.surname, person.gender,
        person.birthdate, person.username, person.city
    )

    private fun getPersonalInformation(id: Int) {
        uiScope.launch {
            try {
                val response = ApiService.retrofitPersonService.getPerson(id, getToken()).await()
                _person.value = response
            } catch (e: Exception) {
                _response.value = "Failure: an error occur"
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("IdentityViewModel", "destroyed")
        viewModelJob.cancel()
    }
}