package com.suveybesena.schoolchattingapp.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
            viewModel._uiState.collect { state->
                state.loggedIn.let { loggedIn->
                    if (loggedIn == true){
                        goTeachersFragment()
                    }

                }
            }
        }
    }

    private fun goTeachersFragment() {
      findNavController().navigate(R.id.action_loginFragment_to_teachersFragment)
    }

    private fun initListeners() {
        binding.bvSignIn.setOnClickListener {
            logIn()
        }
    }

    private fun logIn() {
        binding.apply {
            val loginModel = LoginModel(etEmail.text.toString(), etPassword.text.toString())
            viewModel.handlEvent(LoginEvent.Login(loginModel))
        }

    }


}