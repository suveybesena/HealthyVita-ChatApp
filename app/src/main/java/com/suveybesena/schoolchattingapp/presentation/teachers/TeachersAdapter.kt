package com.suveybesena.schoolchattingapp.presentation.teachers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.ItemTeacherBinding

class TeachersAdapter (private val questionClick : OnItemQuestionClick) : RecyclerView.Adapter<TeachersAdapter.TeacherVH>() {
    class TeacherVH(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<TeacherFeedModel>() {
        override fun areItemsTheSame(
            oldItem: TeacherFeedModel,
            newItem: TeacherFeedModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: TeacherFeedModel,
            newItem: TeacherFeedModel
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherVH {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherVH(binding)
    }

    override fun onBindViewHolder(holder: TeacherVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.image.let { url ->
                ivTeacher.downloadImage(url)
            }
            tvField.text = list.field
            tvTeacherName.text = list.name
            bvQuestion.setOnClickListener {
                questionClick.onTeacherItemClick(list)
            }
        }
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }
}