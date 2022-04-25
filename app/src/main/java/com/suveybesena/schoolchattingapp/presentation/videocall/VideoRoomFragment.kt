package com.suveybesena.schoolchattingapp.presentation.videocall

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.databinding.FragmentVideoRoomBinding

class VideoRoomFragment : Fragment() {

    var userRole = 0

    private lateinit var binding: FragmentVideoRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        initListeners()
    }

    private fun initListeners() {
        binding.bvJoinCall.setOnClickListener {
            onSubmit()
        }
    }

    private fun onSubmit() {
        val channelName = binding.etChannel
        val checked = binding.radioGroup.checkedRadioButtonId
        val audienceButton = binding.rbAudio

        userRole = if (checked == audienceButton.id) {
            0
        } else {
            1
        }

        val bundle = Bundle()
        val videoModel = VideoCallModel(channelName.text.toString(), userRole)
        bundle.putSerializable("roomInfo", videoModel)
        findNavController().navigate(R.id.action_videoRoomFragment_to_videoCallFragment, bundle)

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.CAMERA),
            22
        )
    }
}