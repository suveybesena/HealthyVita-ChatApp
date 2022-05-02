package com.suveybesena.schoolchattingapp.presentation.doctors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.ItemDoctorBinding

class DoctorsAdapter(private val questionClick: OnItemQuestionClick) :
    RecyclerView.Adapter<DoctorsAdapter.DoctorsVH>() {
    class DoctorsVH(val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<DoctorFeedModel>() {
        override fun areItemsTheSame(
            oldItem: DoctorFeedModel,
            newItem: DoctorFeedModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: DoctorFeedModel,
            newItem: DoctorFeedModel
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsVH {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorsVH(binding)
    }

    override fun onBindViewHolder(holder: DoctorsVH, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            list.image.let { url ->
                if (url != null) {
                    ivDoctor.downloadImage(url)
                }
            }
            tvField.text = list.field
            tvDoctorName.text = list.name
            bvQuestion.setOnClickListener {
                questionClick.onDoctorItemClick(list)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}