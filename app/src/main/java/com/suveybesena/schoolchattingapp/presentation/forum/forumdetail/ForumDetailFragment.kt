package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.databinding.FragmentForumDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForumDetailFragment : Fragment() {
    private lateinit var binding: FragmentForumDetailBinding
    private val args: ForumDetailFragmentArgs by navArgs()
    private val viewModel: ForumDetailViewModel by viewModels()
    private lateinit var adapter: ForumAnswersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        initListeners()
    }

    private fun setupRecyclerView() {
        adapter = ForumAnswersAdapter()
        binding.apply {
            rvForumDetailAnswers.adapter = adapter
        }
    }

    private fun initListeners() {
        binding.bvSent.setOnClickListener {
            addAnswer()
        }
    }

    private fun addAnswer() {
        val forumInfo = args.forumInfo
        val answer = binding.etAnswer.text.toString()
        val messageId = forumInfo.messageId
        val forumAnswer = messageId?.let { ForumDetailModel(it, answer) }
        forumAnswer?.let { ForumDetailEvent.AddForumMessage(it) }?.let { viewModel.handleEvent(it) }
    }

    private fun observeData() {
        val forumInfo = args.forumInfo
        binding.apply {
            tvForum.text = forumInfo.forumMessage
            tvForumUser.text = forumInfo.userName
            forumInfo.userImage?.let { ivForumUser.downloadImage(it) }
        }

        forumInfo.messageId?.let { ForumDetailEvent.FetchForumMessage(it) }
            ?.let { viewModel.handleEvent(it) }
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.answerList.let { answer ->
                    adapter.differ.submitList(answer)
                }
            }
        }
    }
}