package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.ItemAnswerBinding
import com.suveybesena.schoolchattingapp.databinding.ItemForumBinding

class ForumAnswersAdapter : RecyclerView.Adapter<ForumAnswersAdapter.AnswersVH>() {
    class AnswersVH(val binding : ItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    private val differCallBack = object : DiffUtil.ItemCallback<ForumDetailModel>() {
        override fun areItemsTheSame(
            oldItem: ForumDetailModel,
            newItem: ForumDetailModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ForumDetailModel,
            newItem: ForumDetailModel
        ): Boolean {
            return true
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumAnswersAdapter.AnswersVH {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnswersVH(binding)
    }

    override fun onBindViewHolder(holder: AnswersVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            tvForum.text =list.message
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}