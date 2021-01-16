package com.projectPlant.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectPlant.R
import com.projectPlant.ToastRender
import com.projectPlant.databinding.FragmentConnectionBinding
import com.projectPlant.model.Person
import com.projectPlant.modelView.UserConnectionModelView
import com.projectPlant.viewModelFactory.UserConnectionViewModelFactory

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ConnectionFragment : Fragment() {
    private lateinit var binding: FragmentConnectionBinding
    private lateinit var viewModelUser: UserConnectionModelView

    companion object {
        var isInscriptionFragmentation: Boolean = false
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_connection, container, false)

        val viewModelFactory =
            UserConnectionViewModelFactory(binding.viewModel?.user?.value, requireContext())
        viewModelUser =
            ViewModelProvider(this, viewModelFactory).get(UserConnectionModelView::class.java)

        binding.viewModel = viewModelUser

        //login validation
        binding.btValidate.setOnClickListener {
            validate()
        }

        //change between create account and login
        binding.btRedirect.setOnClickListener {
            isInscriptionFragmentation = !isInscriptionFragmentation

            if (isInscriptionFragmentation) {
                binding.tvDescription.text = getString(R.string.have_account)
                binding.tvTitle.text = getString(R.string.inscription)
                binding.btValidate.text = getString(R.string.create)
                binding.btRedirect.text = getString(R.string.login_page)
                binding.tiPassword.error = null
                binding.tiEmail.error = null
            } else {
                binding.tvDescription.text = getString(R.string.dont_have_account)
                binding.tvTitle.text = getString(R.string.connection)
                binding.btValidate.text = getString(R.string.connection)
                binding.btRedirect.text = getString(R.string.create)
                binding.tiPassword.error = null
                binding.tiEmail.error = null
            }
        }


        viewModelUser.response.observe(viewLifecycleOwner, Observer { response ->
            if (response.startsWith("Success: ")) {
                viewModelUser.setElementString("_TOKEN", viewModelUser.token.value!!)

                val idPerson = viewModelUser.idPerson.value
                if (idPerson == null || idPerson == 0) {
                    goToInscriptionPage(viewModelUser.user.value?.username!!)

                } else {
                    goToHomePage(idPerson)
                    viewModelUser.setElementInt("_PERSON", idPerson)
                }
            }
        })

        viewModelUser.response.observe(viewLifecycleOwner, Observer {

            ToastRender.toast(this.requireContext(), it).show()
        })

        return binding.root
    }

    private fun emailValidation(setError: Boolean = true): Boolean {
        if (setError && !binding.viewModel?.user?.value?.username.isValidEmail()) {
            binding.tiEmail.error = getString(R.string.emailError)
        }
        return binding.viewModel?.user?.value?.username.isValidEmail()
    }

    private fun passwordValidation(setError: Boolean = true): Boolean {
        if (setError && !binding.viewModel?.user?.value?.password.isValidPassword()) {
            binding.tiPassword.error = getString(R.string.passwordError)
        }
        return binding.viewModel?.user?.value?.password.isValidPassword()
    }

    //email validator
    private fun CharSequence?.isValidEmail() =
        !this?.isEmpty()!! || !isBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    //password validator
    private fun CharSequence?.isValidPassword() = !isNullOrBlank() && this!!.length >= 6

    private fun goToInscriptionPage(username: String) {
        findNavController().navigate(
            ConnectionFragmentDirections.actionConnectionFragmentToInscriptionFragment(
                Person(username = username)
            )
        )

    }

    private fun goToHomePage(id: Int) {

        findNavController().navigate(
            ConnectionFragmentDirections.actionConnectionFragmentToHomeFragment(
                id
            )
        )
    }

    //call to the api
    private fun validate() {
        passwordValidation()
        emailValidation()
        if (viewModelUser.user.value?.username.isValidEmail() && viewModelUser.user.value?.password.isValidPassword()) {
            viewModelUser.onValidate(!isInscriptionFragmentation)
        }
    }
}