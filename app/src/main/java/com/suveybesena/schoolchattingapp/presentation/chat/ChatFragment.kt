package com.suveybesena.schoolchattingapp.presentation.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            bvSent.setOnClickListener {
                saveMessage()
            }
        }
    }

    private fun saveMessage() {
        val message = binding.etMessage.text.toString()
        val date = com.google.firebase.Timestamp.now()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val selectedUserInfo = args.teacherInfo

        val messageModel =
            MessageModel(
                message,
                currentUserId,
                "", date, selectedUserInfo.id
            )

        viewModel.handleEvent(ChatEvent.AddMessageToFirebase(messageModel, currentUserId))

        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.messageList.let { list ->
                    chatAdapter.differ.submitList(list)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.apply {
            rvChat.adapter = chatAdapter
        }
    }

    private fun observeData() {
        val selectedUserInfo = args.teacherInfo
        viewModel.handleEvent(ChatEvent.FetchMessages(selectedUserInfo.id))
        binding.apply {
            ivProfile.downloadImage(selectedUserInfo.image)
            tvTeacherName.text = selectedUserInfo.name
        }
    }
}