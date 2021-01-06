package com.projectPlant.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projectPlant.model.User
import com.projectPlant.modelView.UserConnectionModelView

class UserConnectionViewModelFactory(
        private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserConnectionModelView::class.java)) {
            return UserConnectionModelView(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}