package com.projectPlant.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectPlant.R
import com.projectPlant.databinding.FragmentInscriptionBinding
import com.projectPlant.model.Person
import com.projectPlant.modelView.PersonViewModel
import com.projectPlant.viewModelFactory.PersonViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InscriptionFragment : Fragment() {
    private lateinit var binding: FragmentInscriptionBinding
    private lateinit var personViewModel: PersonViewModel

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inscription, container, false)
        val args = InscriptionFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = PersonViewModelFactory(
            args.person.idperson,
            args.person.username,
            this.requireContext()
        )
        personViewModel = ViewModelProvider(this, viewModelFactory).get(PersonViewModel::class.java)

        binding.viewModel = personViewModel

        if (args.person.idperson == 0) {
            binding.ivClose.isVisible = false
        }

        binding.tiGender.setOnClickListener {
            if (binding.tiGender.hint == getString(R.string.woman)) {
                binding.tiGender.hint = getString(R.string.man)
                personViewModel.person.value?.gender = "M"
            } else {
                binding.tiGender.hint = getString(R.string.woman)
                personViewModel.person.value?.gender = "F"
            }
        }

        binding.tiBirthDay.setOnClickListener {
            // calender class's instance and get current date , month and year from calender
            datePicker()
        }

        binding.tiBirthDay.setOnFocusChangeListener { _, b ->
            // calender class's instance and get current date , month and year from calender
            if (b) {
                datePicker()
            }
        }

        binding.tiBirthDay.setOnKeyListener { view: View, i: Int, keyEvent: KeyEvent ->
            datePicker()
            return@setOnKeyListener false
        }

        binding.btValidate.setOnClickListener {
            validate(binding.viewModel?.person?.value!!)
        }

        personViewModel.person.observe(viewLifecycleOwner, Observer {
            if (it.idperson != 0) {
                findNavController().navigate(
                    InscriptionFragmentDirections.actionInscriptionFragmentToHomeFragment(
                        it.idperson
                    )
                )
            }
        })

        personViewModel.response.observe(viewLifecycleOwner, Observer {

        })


        return binding.root

    }


    private fun datePicker() {
        val c: Calendar = Calendar.getInstance()
        val cYear: Int = c.get(Calendar.YEAR) // current year
        val cMonth: Int = c.get(Calendar.MONTH) // current month
        val cDay: Int = c.get(Calendar.DAY_OF_MONTH) // current day
        // date picker dialog

        val dpd = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                val dateString = "$dayOfMonth-${getMonth(monthOfYear + 1)}-$year"
                binding.tiBirthDay.setText(dateString)

                val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy", Locale.FRENCH)
                binding.viewModel?.setBirthDate(simpleDateFormat.parse(dateString)!!)
            },
            cYear,
            cMonth,
            cDay
        )

        dpd.show()

    }

    private fun getMonth(int: Int): String {
        return if (int < 10) {
            "0$int"
        } else {
            int.toString()
        }
    }

    private fun validate(person: Person) {
        binding.viewModel?.addPersonalInformation(person)
    }
}