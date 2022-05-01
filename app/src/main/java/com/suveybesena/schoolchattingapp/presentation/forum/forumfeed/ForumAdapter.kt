package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.databinding.ItemForumBinding

class ForumAdapter(val itemClick: OnItemForumClick) : RecyclerView.Adapter<ForumAdapter.ForumVH>() {
    class ForumVH(val binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ForumModel>() {
        override fun areItemsTheSame(
            oldItem: ForumModel,
            newItem: ForumModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ForumModel,
            newItem: ForumModel
        ): Boolean {
            return true
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumVH {
        val binding = ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForumVH(binding)
    }

    override fun onBindViewHolder(holder: ForumVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            tvForumUser.text = list.userName
            tvForum.text = list.forumMessage
            ivForumUser.downloadImage(list.userImage)

        }
        holder.itemView.setOnClickListener {
            list.let { forumInfo ->
                itemClick.onItemForumClick(forumInfo)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}