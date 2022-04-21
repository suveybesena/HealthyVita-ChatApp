package com.suveybesena.schoolchattingapp.presentation.teachers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.databinding.FragmentTeachersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeachersFragment : Fragment() {

    private lateinit var binding: FragmentTeachersBinding
    private val viewModel: TeachersViewModel by viewModels()
    private lateinit var teachersAdapter: TeachersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeachersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeData()
    }

    private fun setupRecyclerView() {
        teachersAdapter = TeachersAdapter(object : OnItemQuestionClick{
            override fun onTeacherItemClick(teacherInfo: TeacherFeedModel) {
                goChatFragment(teacherInfo)
            }
        })
        binding.apply {
            rvTeachers.adapter = teachersAdapter
        }
    }

    private fun goChatFragment(teacherInfo : TeacherFeedModel) {
        val bundle = Bundle()
         bundle.putSerializable("teacherInfo",teacherInfo)
        findNavController().navigate(R.id.action_teachersFragment_to_chatFragment,bundle)
    }
    private fun observeData() {
        viewModel.handleEvent(TeachersFeedEvent.FetchTeachersData)
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.list.let { list ->
                    teachersAdapter.differ.submitList(list)
                }
            }
        }
    }
    private fun initListeners() {

    }
}