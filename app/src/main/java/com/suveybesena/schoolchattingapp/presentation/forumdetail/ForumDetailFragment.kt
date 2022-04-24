package com.suveybesena.schoolchattingapp.presentation.forumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.FragmentForumDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForumDetailFragment : Fragment() {
    private lateinit var binding: FragmentForumDetailBinding
    private val args: ForumDetailFragmentArgs by navArgs()
    private val viewModel: ForumDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        val forumInfo = args.forumInfo
        binding.apply {
            tvForum.text = forumInfo.forumMessage
            tvForumUser.text = forumInfo.userName
            ivForumUser.downloadImage(forumInfo.userImage)
        }
    }
}