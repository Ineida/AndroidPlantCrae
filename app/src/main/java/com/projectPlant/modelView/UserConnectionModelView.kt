package com.projectPlant.modelView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projectPlant.model.LoginResponse
import com.projectPlant.model.User
import com.projectPlant.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserConnectionModelView(
        _user: User?) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _token

    private val _change = MutableLiveData<Boolean>()
    val change: LiveData<Boolean>
        get() = _change

    private val _idPerson = MutableLiveData<Int>()
    val idPerson: LiveData<Int>
        get() = _idPerson

    private fun initializeUser() {
        uiScope.launch {
            _user.value = User()
            _change.value = false
        }
    }

    init {
        Log.i("Identit" +
                "ViewModel", "created")
        initializeUser()
        _change.value = false
    }

    private fun createAccount() {
        uiScope.launch {
            try {
                val response = ApiService.retrofitService.createAccountAsync(user = user.value!!).await()
                extractData(response = response)
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    private fun login() {
        uiScope.launch {
            try {
                val response = ApiService.retrofitService.loginAsync(user = user.value!!).await()
                extractData(response)
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }


    private fun extractData(response: LoginResponse) {
        try {
            _token.value = response.token
            _idPerson.value = response.idPerson

            _response.value = "Success: your account have been created"

        } catch (e: Exception) {
            _response.value = "Failure: ${e.message}"
        }
    }

    fun onValidate(login: Boolean = true) {
        uiScope.launch {
            if (login) {
                login()
            } else {
                createAccount()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("IdentityViewModel", "destroyed")
        viewModelJob.cancel()
    }
}