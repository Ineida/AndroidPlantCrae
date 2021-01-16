package com.projectPlant.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectPlant.R
import com.projectPlant.databinding.FragmentAddPlantBinding
import com.projectPlant.model.PlantPersonSimple
import com.projectPlant.model.PlantSimple
import com.projectPlant.modelView.AddPlantModelView
import com.projectPlant.viewModelFactory.AddPlantModelViewFactory
import java.util.*

class AddPlantFragment : Fragment() {

    lateinit var viewModel: AddPlantModelView
    lateinit var binding: FragmentAddPlantBinding

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_plant, container, false)
        val application = requireNotNull(this.activity).application
        val args = AddPlantFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = AddPlantModelViewFactory(
            args.plant,
            args.myPlant,
            application = application,
            _context = requireContext()
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddPlantModelView::class.java)
        binding.viewModel = viewModel

        if (viewModel.myPlant.value?.id == 0) {
            binding.btAdd.text = getString(R.string.add)
            binding.btDelete.visibility = View.GONE
            binding.btWater.text = getString(R.string.cancel)

        } else {
            binding.btAdd.text = getString(R.string.update)
            binding.btWater.text = getString(R.string.Water)
            disable()
            binding.spinner.visibility = View.GONE
        }

        var listPlant = listOf<PlantSimple>()
        lateinit var adapter: ArrayAdapter<String>
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val selected = listPlant.get(position)
                    viewModel.setPlanSimple(selected)
                    setInfo(selected)
                    disable()
                    binding.spinner.setSelection(0)
                }
            }
        }

        binding.btAdd.setOnClickListener {
            if (viewModel.myPlant.value?.id == 0) {
                add(viewModel.simplePlant.value!!, viewModel.myPlant.value!!)
            } else {
                edit(viewModel.myPlant.value!!)
            }
        }

        binding.btWater.setOnClickListener {
            if (viewModel.myPlant.value?.id != 0) {
                val id = if (viewModel.myPlant.value?.id == null) 0 else viewModel.myPlant.value?.id
                val quantity: Double =
                    if (viewModel.myPlant.value?.waterMin == null) 0.05 else viewModel.myPlant.value?.waterMin!!.toDouble()
                water(id, quantity)

            } else {
                findNavController().navigate(
                    AddPlantFragmentDirections.actionAddPlantFragmentToHomeFragment(
                        args.myPlant.idPerson
                    )
                )
            }
        }

        binding.btDelete.setOnClickListener {
            val id = viewModel.myPlant.value?.id
            delete(id)
        }

        binding.ivClosePlant.setOnClickListener {
            findNavController().navigate(
                AddPlantFragmentDirections.actionAddPlantFragmentToHomeFragment(
                    args.myPlant.idPerson
                )
            )
        }

        binding.swOutside.setOnClickListener {
            viewModel.myPlant.value?.outSide = !viewModel.myPlant.value?.outSide!!
        }

        binding.tiNameP.setOnClickListener {
            viewModel.setPlanSimple(PlantSimple())
            enable()
        }

        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.startsWith("Success:")) {
                findNavController().navigate(
                    AddPlantFragmentDirections.actionAddPlantFragmentToHomeFragment(
                        args.myPlant.idPerson
                    )
                )
            }

            Toast.makeText(
                requireContext(),
                it, Toast.LENGTH_LONG
            )
        })

        viewModel.plantList.observe(viewLifecycleOwner, Observer {
            listPlant = it
            adapter = ArrayAdapter<String>(requireContext(), R.layout.item_plant, plant(it))
            binding.spinner.adapter = adapter
            ///binding.spinner.
        })

        return binding.root
    }

    fun add(plant: PlantSimple, myPlant: PlantPersonSimple) {
        if (isPlantValid(plant) && isPlantValid(myPlant)) {
            viewModel.add(plant, myPlant)
        }
    }

    private fun water(int: Int?, double: Double) {
        if (0 != id) {
            viewModel.water(id, double)
        }
    }

    fun delete(int: Int?) {
        viewModel.delete(int)
    }

    fun isPlantValid(plant: PlantSimple): Boolean {
        if (plant.humidityMax.isNullOrBlank()) {
            binding.tiMaxH.error = "Please indicate the humidity minimum for this plant"
            return false
        } else {
            binding.tiMaxH.error = null
        }

        if (plant.humidityMin.isNullOrBlank()) {
            binding.tiMinH.error = "Please indicate the humidity maximum for this plant"
            return false

        } else {
            binding.tiMinH.error = null
        }

        if (plant.temperatureMax.isNullOrBlank()) {
            binding.tiMaxT.error = "Please indicate the temperature minimum for this plant"
            return false

        } else {
            binding.tiMaxT.error = null

        }

        if (plant.temperatureMax.isNullOrBlank()) {
            binding.tiMinT.error = "Please indicate the temperature maximum for this plant"
            return false

        } else {
            binding.tiMinT.error = null
        }

        if (plant.name.isNullOrBlank()) {
            binding.tiNameP.error = "Please indicate the plant name"
            return false

        } else {
            binding.tiNameP.error = null
        }
        return true
    }

    fun isPlantValid(plant: PlantPersonSimple): Boolean {
        if (plant.waterMax.isNullOrBlank()) {
            binding.tiMaxW.error = "Please indicate the minimum of water for this plant"
            return false

        } else {
            binding.tiMaxW.error = null
        }

        if (plant.waterMax.isNullOrBlank()) {
            binding.tiMinW.error = "Please indicate the maximum of water for this plant"
            return false

        } else {
            binding.tiMinW.error = null
        }
        return true
    }

    fun edit(plant: PlantPersonSimple) {
        if (isPlantValid(plant)) {
            viewModel.edit(plant)
        }
    }

    fun plant(list: List<PlantSimple>): List<String> {
        var result = ArrayList<String>()
        result.add(getString(R.string.prompt))
        for (plant in list) {
            result.add(plant.toString())
        }
        return result
    }

    fun setInfo(plant: PlantSimple) {
        binding.tiMaxT.setText(plant.temperatureMax)
        binding.tiMinT.setText(plant.temperatureMin)
        binding.tiMinH.setText(plant.humidityMin)
        binding.tiMaxH.setText(plant.humidityMax)
        binding.tiNameP.setText(plant.name)
    }

    fun disable() {
        binding.tiMaxT.isEnabled = false
        binding.tiMinT.isEnabled = false
        binding.tiMinH.isEnabled = false
        binding.tiMaxH.isEnabled = false
    }

    fun enable() {
        binding.tiMaxT.isEnabled = true
        binding.tiMinT.isEnabled = true
        binding.tiMinH.isEnabled = true
        binding.tiMaxH.isEnabled = true
    }

}