package com.suveybesena.schoolchattingapp.presentation.chat

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import com.suveybesena.schoolchattingapp.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var contentResolver: ContentResolver
    var pickedImage: Uri? = null
    var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentResolver = requireActivity().contentResolver
        setupRecyclerView()
        observeData()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            bvSent.setOnClickListener {
                saveMessage()
            }
            bvImageSent.setOnClickListener {
                ivPickedImage()
            }
        }
    }

    private fun saveMessage() {
        val selectedUserInfo = args.doctorInfo
        val message = binding.etMessage.text.toString()
        val date = System.currentTimeMillis()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (message != "") {
            val messageModel =
                currentUserId?.let { currentUser ->
                    pickedImage?.let { uri ->
                        MessageModel(
                            message, currentUser,
                            uri, date, selectedUserInfo.id
                        )
                    } ?: run {
                        MessageModel(
                            message, currentUser,
                            null, date, selectedUserInfo.id
                        )
                    }
                }
            messageModel?.let { messages ->
                ChatEvent.AddMessageToFirebase(messages)
            }
                ?.let { event ->
                    viewModel.handleEvent(event)
                }
            currentUserId?.let { currentUser ->
                ChatEvent.FetchMessage(currentUser, selectedUserInfo.id)
            }
                ?.let { event ->
                    viewModel.handleEvent(event)
                }
        }
        binding.etMessage.setText("")
        pickedImage = null
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.apply {
            rvChat.adapter = chatAdapter
            rvChat.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    private fun observeData() {
        val selectedUserInfo = args.doctorInfo
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        binding.apply {
            ivProfile.downloadImage(selectedUserInfo.image)
            tvDoctorName.text = selectedUserInfo.name
        }

        currentUserId?.let { ChatEvent.FetchMessage(it, selectedUserInfo.id) }
            ?.let { viewModel.handleEvent(it) }

        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.messageList.let { list ->
                    if (list != null) {
                        list.forEach { messageInfo ->
                            if (messageInfo.receiverId == selectedUserInfo.id) {
                                chatAdapter.differ.submitList(list)
                                chatAdapter.notifyDataSetChanged()

                            }
                        }
                    }
                }
                state.isLoading.let { loading->
                    if (loading == true) {
                        binding.pgBar.visibility = View.VISIBLE
                    } else {
                        binding.pgBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun ivPickedImage() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            pickedImage = data.data

            if (pickedImage != null) {

                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedImage!!)
                    bitmap = ImageDecoder.decodeBitmap(source)

                } else {
                    bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, pickedImage)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}