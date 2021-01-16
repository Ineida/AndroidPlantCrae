package com.projectPlant.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectPlant.R
import com.projectPlant.databinding.FragmentHomeBinding
import com.projectPlant.model.PlantPersonSimple
import com.projectPlant.model.PlantSimple
import com.projectPlant.model.Weather
import com.projectPlant.model.WeatherInfo
import com.projectPlant.modelView.HomePageViewModel
import com.projectPlant.viewModelFactory.HomePageViewModelFactory


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomePageViewModel
    var city: String = "Paris"

    @SuppressLint("SetTextI18n", "ResourceAsColor", "ResourceType", "ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val application = requireNotNull(this.activity).application
        val args = HomeFragmentArgs.fromBundle(requireArguments())

        // init view model
        val viewModelFactory =
            HomePageViewModelFactory(args.idPerson, application, this.requireContext())
        viewModel = ViewModelProvider(
            this.viewModelStore,
            viewModelFactory
        ).get(HomePageViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //init plant adpater
        val plantFragment: PlantFragment =
            childFragmentManager.findFragmentById(R.id.frag_plant) as PlantFragment
        plantFragment.adapter.apply {
            //enable button click
            seeClick = {
                goToAddPlant(
                    it.plantByIdplant,
                    PlantPersonSimple(
                        it.id, it.personByIdperson.idperson,
                        it.plantByIdplant.id, it.active, it.outSide,
                        it.waterMax.toString(), it.waterMin.toString()
                    )
                )
            }
        }

        //header events
        val headerFragment: HeaderFragment =
            childFragmentManager.findFragmentById(R.id.frag_header) as HeaderFragment

        viewModel.person.observe(viewLifecycleOwner, Observer {
            headerFragment.binding.tvWelcome.text = "Welcome " + it.name
            viewModel.setElementString("_NAME", it.name)
            viewModel.getWeather()
        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                requireContext(),
                it, Toast.LENGTH_LONG
            )
        })

        viewModel.weather.observe(viewLifecycleOwner, Observer {
            viewModel.setElementString("_DAY", it.day)
            viewModel.setElementString("_HUMIDITY", it.avghumidity)
            viewModel.setElementString("_TAV", it.avgtemp_c)
            viewModel.setElementString("_TMAX", it.maxtemp_c)
            viewModel.setElementString("_TMIN", it.mintemp_c)
            viewModel.setElementString("_WIND", it.maxwind_kph)
            viewModel.setElementString("_ICON", it.icon)
            viewModel.setElementString("_WEATHER", it.condition.text)
        })

        if (viewModel.weather.value == null) {

            viewModel.setWeather(
                Weather(
                    condition = WeatherInfo(text = viewModel.getStringElement("_WEATHER")),
                    avgtemp_c = viewModel.getStringElement("_TAV")
                )
            )

            val weatherFragment: WeatherFragment =
                childFragmentManager.findFragmentById(R.id.frag_weather) as WeatherFragment
            weatherFragment.setWeather(
                viewModel.getStringElement("_DAY"),
                viewModel.getStringElement("_WEATHER"),
                viewModel.getStringElement("_TAV"),
                viewModel.getStringElement("_TMAX"),
                viewModel.getStringElement("_TMIN"),
                viewModel.getStringElement("_HUMIDITY"),
                viewModel.getStringElement("_WIND")
            )
        }

        if (viewModel.person.value == null) {
            headerFragment.binding.tvWelcome.text = "Welcome " + viewModel.getStringElement("_NAME")
        }

        headerFragment.binding.ivAdd.setOnClickListener {
            goToAddPlant(PlantSimple(), PlantPersonSimple(idPerson = viewModel.getPersonId()))
        }

        binding.btPlant.setOnClickListener {

            it.background = getDrawable(requireContext(), R.drawable.background_borred)
            binding.btWeather.background = getDrawable(requireContext(), R.drawable.background)
            binding.cvWeather.visibility = View.GONE
            binding.cvPlant.visibility = View.VISIBLE
        }

        binding.btWeather.setOnClickListener {

            it.background = getDrawable(requireContext(), R.drawable.background_borred)
            binding.btPlant.background = getDrawable(requireContext(), R.drawable.background)
            binding.cvWeather.visibility = View.VISIBLE
            binding.cvPlant.visibility = View.GONE
        }


        return binding.root
    }

    fun goToAddPlant(plant: PlantSimple, myPlant: PlantPersonSimple) = findNavController().navigate(
        HomeFragmentDirections.actionHomeFragmentToAddPlantFragment(
            myPlant,
            plant
        )
    )

}