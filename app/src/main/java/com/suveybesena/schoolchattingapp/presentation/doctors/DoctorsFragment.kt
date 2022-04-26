package com.suveybesena.schoolchattingapp.presentation.doctors

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
import com.suveybesena.schoolchattingapp.databinding.FragmentDoctorsBinding
import com.suveybesena.schoolchattingapp.presentation.profile.ProfileEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorsBinding
    private val viewModel: DoctorsViewModel by viewModels()
    private lateinit var doctorsAdapter: DoctorsAdapter
    private lateinit var patientsAdapter: PatientMessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDoctorRecyclerView()
        observeData()
        initListeners()
    }

    private fun setupDoctorRecyclerView() {
        doctorsAdapter = DoctorsAdapter(object : OnItemQuestionClick {
            override fun onDoctorItemClick(doctorInfo: DoctorFeedModel) {
                goChatFragment(doctorInfo)
            }
        })
        binding.apply {
            rvConversations.adapter = doctorsAdapter
        }
    }

    private fun setupPatientRecyclerView() {
        patientsAdapter = PatientMessagesAdapter(object : OnItemQuestionClick {
            override fun onDoctorItemClick(doctorInfo: DoctorFeedModel) {
                goChatFragment(doctorInfo)
            }
        })
        binding.apply {
            rvConversations.adapter = patientsAdapter
        }
    }


    private fun goChatFragment(doctorInfo: DoctorFeedModel) {
        val bundle = Bundle()
        bundle.putSerializable("doctorInfo", doctorInfo)
        findNavController().navigate(R.id.action_doctorsFragment_to_chatFragment, bundle)
    }

    private fun observeData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        viewModel.handleEvent(DoctorsFeedEvent.FetchDoctorData(currentUserId))

        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.doctorInfo.let { doctor ->
                    if (doctor?.userId != null) {
                        binding.tvConversation.text = "Bekleyen Görüşmeleriniz"
                        setupPatientRecyclerView()
                        viewModel.handleEvent(DoctorsFeedEvent.FetchPatientMessages(currentUserId))
                        lifecycleScope.launch {
                            viewModel._uiState.collect{state->
                                state.patientMessage.let { list->
                                    patientsAdapter.differ.submitList(list)
                                   println(list)
                                }
                            }
                        }

                    } else {
                        binding.tvConversation.text = "Görüşme başlatmak için doktor seçiniz."
                        setupDoctorRecyclerView()
                        viewModel.handleEvent(DoctorsFeedEvent.FetchDoctorsData)
                        lifecycleScope.launch {
                            viewModel._uiState.collect { state ->
                                state.list.let { list ->
                                    doctorsAdapter.differ.submitList(list)
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    private fun initListeners() {
    }
}