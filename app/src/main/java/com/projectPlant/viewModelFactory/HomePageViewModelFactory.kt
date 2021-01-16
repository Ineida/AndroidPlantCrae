package com.projectPlant.viewModelFactory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projectPlant.modelView.HomePageViewModel


class HomePageViewModelFactory(
    private val id: Int,
    private val application: Application,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(id, application = application, _context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}