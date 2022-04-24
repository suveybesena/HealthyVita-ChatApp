package com.suveybesena.schoolchattingapp.presentation.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.databinding.FragmentDoctorsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorsFragment : Fragment() {

    private lateinit var binding: FragmentDoctorsBinding
    private val viewModel: DoctorsViewModel by viewModels()
    private lateinit var doctorsAdapter: DoctorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeData()
    }

    private fun setupRecyclerView() {
        doctorsAdapter = DoctorsAdapter(object : OnItemQuestionClick {
            override fun onDoctorItemClick(doctorInfo: DoctorFeedModel) {
                goChatFragment(doctorInfo)
            }
        })
        binding.apply {
            rvDoctors.adapter = doctorsAdapter
        }
    }

    private fun goChatFragment(doctorInfo: DoctorFeedModel) {
        val bundle = Bundle()
        bundle.putSerializable("doctorInfo", doctorInfo)
        findNavController().navigate(R.id.action_doctorsFragment_to_chatFragment, bundle)
    }

    private fun observeData() {
        viewModel.handleEvent(DoctorsFeedEvent.FetchDoctorsData)
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.list.let { list ->
                    doctorsAdapter.differ.submitList(list)
                }
            }
        }
    }

    private fun initListeners() {
    }
}