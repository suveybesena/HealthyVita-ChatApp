package com.suveybesena.schoolchattingapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.FragmentProfileBinding
import com.suveybesena.schoolchattingapp.presentation.workmanager.RunWorker
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var request: WorkRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
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
            showDialog()
        }
        val workCondition = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        request = PeriodicWorkRequestBuilder<RunWorker>(1, TimeUnit.HOURS)
            .setConstraints(workCondition)
            .build()

        binding.svReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isChecked == true) {
                setWorkReminder(request)
            } else {
                cancelReminder(request)
            }
        }
    }

    private fun showDialog() {
        val dialog = CustomDialogFragment()
        dialog.show(childFragmentManager, "customDialog")
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

    private fun setWorkReminder(uploadRequest: WorkRequest) {
        WorkManager.getInstance(requireContext())
            .enqueue(uploadRequest)
        Snackbar.make(
            requireView(),
            "Hourly notifications turned on.",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun cancelReminder(uploadRequest: WorkRequest) {
        WorkManager.getInstance(requireContext())
            .cancelWorkById(uploadRequest.id)
        Snackbar.make(
            requireView(),
            "Hourly notifications are turned off.",
            Snackbar.LENGTH_LONG
        ).show()
    }
}