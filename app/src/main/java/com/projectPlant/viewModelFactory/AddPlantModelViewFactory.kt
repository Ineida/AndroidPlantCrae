package com.projectPlant.viewModelFactory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projectPlant.model.PlantPersonSimple
import com.projectPlant.model.PlantSimple
import com.projectPlant.modelView.AddPlantModelView

class AddPlantModelViewFactory(
    private var plant: PlantSimple, private var myPersonPlant: PlantPersonSimple,
    private var application: Application, private var _context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlantModelView::class.java)) {
            return AddPlantModelView(plant, myPersonPlant, application, _context = _context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}