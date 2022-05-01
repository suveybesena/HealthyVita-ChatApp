package com.suveybesena.schoolchattingapp.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observeData()
    }

    private fun observeData() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.action_guidanceFragment_to_doctorsFragment)

        }
    }

    private fun initListeners() {
        binding.apply {
            bvStudents.setOnClickListener {
                findNavController().navigate(R.id.action_guidanceFragment_to_patientRegisterFragment)
            }
            bvTeachers.setOnClickListener {
                findNavController().navigate(R.id.action_guidanceFragment_to_doctorRegisterFragment)
            }
        }
    }
}