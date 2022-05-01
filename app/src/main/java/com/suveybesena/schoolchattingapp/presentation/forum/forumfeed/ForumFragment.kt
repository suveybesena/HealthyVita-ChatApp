package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

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
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.databinding.FragmentForumBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding
    private val viewModel: ForumViewModel by viewModels()
    private lateinit var adapter: ForumAdapter
    private lateinit var userImage: String
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        initListeners()

    }

    private fun observeData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        viewModel.handleEvent(ForumEvent.GetForumData)
        viewModel.handleEvent(ForumEvent.GetPatientData(currentUserId))
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.patientInfo.let { patient ->
                    if (patient != null) {
                        binding.apply {
                            tvCurrentUser.text = patient.name
                            userName = patient.name
                            ivUser.downloadImage(patient.image)
                            userImage = patient.image
                        }
                    } else {
                        viewModel.handleEvent(ForumEvent.GetDoctorData(currentUserId))
                        state.doctorInfo.let { doctor ->
                            if (doctor != null) {
                                binding.apply {
                                    tvCurrentUser.text = doctor.name
                                    userName = doctor.name
                                    ivUser.downloadImage(doctor.image)
                                    userImage = doctor.image
                                }
                            }
                        }
                    }
                }
                state.forumInfo.let { list ->
                    adapter.differ.submitList(list)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ForumAdapter(object : OnItemForumClick {
            override fun onItemForumClick(forumModel: ForumModel) {
                goForumDetails(forumModel)
            }
        })
        binding.apply {
            rvForum.adapter = adapter
        }
    }

    fun goForumDetails(forumModel: ForumModel) {
        val bundle = Bundle()
        bundle.putSerializable("forumInfo", forumModel)
        findNavController().navigate(R.id.action_forumFragment_to_forumDetailFragment, bundle)
    }

    private fun initListeners() {
        binding.bvSent.setOnClickListener {
            saveForumData()
        }
    }

    private fun saveForumData() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val forumMessage = binding.etForum.text.toString()
        val time = System.currentTimeMillis()
        val messageId = UUID.randomUUID().toString()
        val forum = currentUserId?.let { it1 ->
            ForumModel(
                forumMessage,
                it1,
                time,
                messageId,
                userImage,
                userName
            )
        }
        viewModel.handleEvent(ForumEvent.AddForumDataToFirebase(forum))
        observeData()
        binding.etForum.setText("")
    }
}