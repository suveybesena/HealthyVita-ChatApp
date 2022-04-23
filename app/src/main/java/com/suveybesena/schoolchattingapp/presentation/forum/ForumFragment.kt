package com.suveybesena.schoolchattingapp.presentation.forum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.FragmentForumBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
    ): View? {
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
        viewModel.handleEvent(ForumEvent.GetStudentData(currentUserId))

        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.currentStudentInfo.let { currentUser ->
                    binding.apply {
                        if (currentUser != null) {
                            tvCurrentUser.text = currentUser.name
                            userName = currentUser.name
                            ivUser.downloadImage(currentUser.image)
                            userImage = currentUser.image
                        }
                    }
                }
                state.forumInfo.let { list ->
                    adapter.differ.submitList(list)
                    println(list)
                }
                println(state.error)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ForumAdapter()
        binding.apply {
            rvForum.adapter = adapter
        }
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