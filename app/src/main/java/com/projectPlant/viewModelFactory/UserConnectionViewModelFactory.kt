package com.projectPlant.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projectPlant.model.User
import com.projectPlant.modelView.UserConnectionModelView

class UserConnectionViewModelFactory(
    private val user: User?,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserConnectionModelView::class.java)) {
            return UserConnectionModelView(user, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}