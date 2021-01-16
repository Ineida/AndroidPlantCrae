package com.projectPlant.modelView

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectPlant.model.LoginResponse
import com.projectPlant.model.Storage
import com.projectPlant.model.User
import com.projectPlant.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class UserConnectionModelView(
    _user: User?, context: Context
) : Storage(context) {
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
        get() = _response

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
        Log.i("UserConnectionModelView", "created")
        initializeUser()
        _change.value = false
    }

    private fun createAccount() {
        uiScope.launch {
            try {
                val response =
                    ApiService.retrofitUserService.createAccountAsync(user = user.value!!).await()
                extractData(response = response)
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    private fun login() {
        uiScope.launch {
            try {
                val response =
                    ApiService.retrofitUserService.loginAsync(user = user.value!!).await()
                extractData(response)
            } catch (e: SocketTimeoutException) {
                _response.value = "Failure: please check your internet connection"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }


    private fun extractData(response: LoginResponse) {
        try {
            if (response.idPerson != null && response.idPerson != 0) {
                _idPerson.value = response.idPerson
            }
            _token.value = response.token
            _response.value = "Success: you are logged"

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
        Log.i("UserConnectionModelView", "destroyed")
        viewModelJob.cancel()
    }
}