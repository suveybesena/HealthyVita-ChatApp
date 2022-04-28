package com.suveybesena.schoolchattingapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.loggedIn.let { loggedIn ->
                    if (binding.etEmail.text.toString() != "") {
                        if (binding.etPassword.text.toString() != "")
                            if (loggedIn == true) {
                                goDoctorsFragment()
                            } else {
                                Snackbar.make(
                                    requireView(),
                                    "Email ve şifre giriniz.",
                                    Snackbar.LENGTH_LONG
                                )
                            }
                    }
                }
                state.isLoading.let {
                    it
                    if (it == true) {
                        binding.pgBar.visibility = View.VISIBLE
                    } else {
                        binding.pgBar.visibility = View.GONE
                    }

                }
                state.error.let { errorMessage ->
                    if (errorMessage != null) {
                        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun goDoctorsFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_doctorsFragment)
    }

    private fun initListeners() {
        binding.bvSignIn.setOnClickListener {
            logIn()
        }
        binding.bvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_guidanceFragment)
        }
    }

    private fun logIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val loginModel = LoginModel(email, password)
        viewModel.handleEvent(LoginEvent.Login(loginModel))
    }
}