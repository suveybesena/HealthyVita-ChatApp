package com.suveybesena.schoolchattingapp.presentation.videocall

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.common.Constants.APP_ID
import com.suveybesena.schoolchattingapp.common.Constants.PERMISSION_REQ_ID_CAMERA
import com.suveybesena.schoolchattingapp.common.Constants.PERMISSION_REQ_ID_RECORD_AUDIO
import com.suveybesena.schoolchattingapp.common.Constants.TOKEN
import com.suveybesena.schoolchattingapp.databinding.FragmentVideoCallBinding
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import kotlinx.coroutines.launch


class VideoCallFragment : Fragment() {

    private var CHANNEL: String? = null
    private var mRtcEngine: RtcEngine? = null
    private lateinit var binding: FragmentVideoCallBinding
    private var userRole = 0
    private val navArgs: VideoCallFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoCallBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = navArgs.roomInfo
        userRole = bundle.userRole
        CHANNEL = bundle.channelName

        if (checkSelfPermission(
                Manifest.permission.RECORD_AUDIO,
                PERMISSION_REQ_ID_RECORD_AUDIO
            ) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)
        ) {
            initializeAndJoinChannel()
        }
        initListeners()
    }

    private fun initListeners() {
        binding.bvCloseCall.setOnClickListener {
            onClose()
        }
        binding.bvMCameraSwitch.setOnClickListener {
            onSwitchCamera()
        }
    }

    private fun onSwitchCamera() {
        mRtcEngine!!.switchCamera()
    }

    private fun onClose() {
        onDestroy()
        findNavController().navigate(R.id.action_videoCallFragment_to_videoRoomFragment)
        Snackbar.make(requireView(), "Video call turned off.", Snackbar.LENGTH_LONG).show()
    }


    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission),
                requestCode
            )
            return false
        }
        return true
    }

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            requireActivity().runOnUiThread {
                setupRemoteVideo(uid)
            }
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)
            onDestroy()
        }
    }

    private fun initializeAndJoinChannel() {
        lifecycleScope.launch {
            try {
                mRtcEngine = RtcEngine.create(context, APP_ID, mRtcEventHandler)
            } catch (e: Exception) {
            }
            mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
            mRtcEngine!!.setClientRole(userRole)
            mRtcEngine!!.enableVideo()
            if (userRole == 1) {
                setupLocaleVideo()
                val localContainer = binding.localVideoViewContainer
                localContainer.isVisible = true
            } else {
                val localContainer = binding.localVideoViewContainer
                localContainer.isVisible = false
            }
            mRtcEngine!!.joinChannel(TOKEN, CHANNEL, "", 0)
        }
    }

    private fun setupLocaleVideo() {
        val localContainer = binding.localVideoViewContainer
        val localFrame = RtcEngine.CreateRendererView(context)
        localFrame.setZOrderMediaOverlay(true)
        localContainer.addView(localFrame)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(localFrame, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun setupRemoteVideo(uid: Int) {
        val remoteContainer = binding.remoteVideoViewContainer
        if (remoteContainer.childCount >= 1) {
            return
        }
        val remoteFrame = RtcEngine.CreateRendererView(context)
        remoteContainer.addView(remoteFrame)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(remoteFrame, VideoCanvas.RENDER_MODE_FIT, uid))
        remoteFrame.tag = uid
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            mRtcEngine?.leaveChannel()
            RtcEngine.destroy()
        }
    }
}