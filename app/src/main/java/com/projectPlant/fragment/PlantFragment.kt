package com.projectPlant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.projectPlant.R
import com.projectPlant.adapter.PlantAdapter
import com.projectPlant.databinding.FragmentPlantBinding
import com.projectPlant.modelView.HomePageViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class PlantFragment : Fragment() {
    lateinit var binding: FragmentPlantBinding
    lateinit var viewModel: HomePageViewModel
    lateinit var parent: HomeFragment
    var adapter = PlantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_plant, container, false
        )
        parent = parentFragment as HomeFragment

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = parent.viewModel
        binding.viewModel = viewModel

        binding.list.adapter = adapter

        parent.viewModel.plantList.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.data = it
            }

            binding.list.isVisible = it?.size != 0
            binding.tvMessage.isVisible = it?.size == 0
        })

    }
}