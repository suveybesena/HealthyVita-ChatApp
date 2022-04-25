package com.suveybesena.schoolchattingapp.presentation.videocall

import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.suveybesena.schoolchattingapp.common.Constants.AGORA_APP_ID
import com.suveybesena.schoolchattingapp.databinding.FragmentVideoCallBinding
import io.agora.rtc.*
import io.agora.rtc.video.VideoCanvas
import java.lang.Exception

class VideoCallFragment : Fragment() {

    private var mRtcEngine: RtcEngine? = null
    private var channelName: String? = null
    private var userRole = 0
    private val args: VideoCallFragmentArgs by navArgs()

    private lateinit var binding: FragmentVideoCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoCallBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAgoraEngineAndJoinChannel()
        initListeners()

    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine!!.leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }

    private fun initListeners() {
        binding.bvMicOff.setOnClickListener { view ->
            onLocalAudioMute(view)
        }

        binding.bvMCameraSwitch.setOnClickListener { view ->
            onSwitchCamera(view)
        }
        binding.bvCloseCall.setOnClickListener {
            onCloseCall()
        }
    }

    private fun onCloseCall() {

    }

    private fun onSwitchCamera(view: View?) {
        mRtcEngine!!.switchCamera()
    }

    private fun onLocalAudioMute(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            // iv.setColorFilter(resources.getColor(R.color.black), PorterDuffColorFilter)
        }

        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }

    private fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine()

        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine!!.setClientRole(userRole)
        mRtcEngine!!.enableVideo()
        if (userRole == 1) {
            setupLocalVideo()
        } else {
            val localVideoCanvas = binding.localVideoViewContainer
            localVideoCanvas.isVisible = false
        }

        joinChannel()
    }

    private fun joinChannel() {
        mRtcEngine!!.joinChannel(AGORA_APP_ID, channelName, null, 0)
    }

    private fun setupLocalVideo() {
        val container = binding.localVideoViewContainer
        val surfaceView = RtcEngine.CreateRendererView(requireContext())
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))

    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            setupRemoteVideo(uid)
            println("lasklfkgaşsg")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            println("işlişgli")
            onRemoteUseLeft()
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            println("Join Channel Success : $uid")
        }
    }

    private fun onRemoteUseLeft() {
        val container = binding.remoteVideoViewContainer
        container.removeAllViews()
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = binding.remoteVideoViewContainer
        if (container.childCount >= 1) {
            return
        }

        val surfaceView = RtcEngine.CreateRendererView(requireContext())
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
        surfaceView.tag = uid
    }

    private fun initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(requireContext(), AGORA_APP_ID, mRtcEventHandler)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeData() {
        val videoInfo = args.roomInfo
        channelName = videoInfo.channelName
        userRole = videoInfo.userRole
    }
}