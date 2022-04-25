package com.suveybesena.schoolchattingapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initListeners()
    }

    private fun initListeners() {
        binding.bvSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun observeData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        viewModel.handleEvent(ProfileEvent.FetchDoctorData(currentUserId))
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.doctorInfo.let { doctor ->
                    if (doctor?.userId != null) {
                        binding.ivUserImage.downloadImage(doctor.image)
                        binding.tvUserName.text = doctor.name
                        binding.tvMail.text = doctor.userMail

                    } else {
                        viewModel.handleEvent(ProfileEvent.FetchPatientData(currentUserId))
                        viewModel._uiState.collect { state ->
                            state.patientInfo.let { patient ->
                                println(patient)
                                if (patient != null) {
                                    binding.ivUserImage.downloadImage(patient.image)
                                }
                                if (patient != null) {
                                    binding.tvUserName.text = patient.name
                                }
                                if (patient != null) {
                                    binding.tvMail.text = patient.userMail
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}