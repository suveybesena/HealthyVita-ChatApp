package com.suveybesena.schoolchattingapp.presentation.guidance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.databinding.FragmentGuidanceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuidanceFragment : Fragment() {
    private lateinit var binding: FragmentGuidanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuidanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
       binding.apply {
           bvStudents.setOnClickListener {
                findNavController().navigate(R.id.action_guidanceFragment_to_studentRegisterFragment)
           }
           bvTeachers.setOnClickListener {
                findNavController().navigate(R.id.action_guidanceFragment_to_teacherRegisterFragment)
           }
       }
    }

}