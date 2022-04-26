package com.suveybesena.schoolchattingapp.presentation.doctors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.ItemDoctorBinding

class PatientMessagesAdapter (private val questionClick: OnItemQuestionClick) : RecyclerView.Adapter<PatientMessagesAdapter.PatientsVH>() {
        class PatientsVH(val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root)

        private val differCallBack = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return true
            }
        }

        val differ = AsyncListDiffer(this, differCallBack)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsVH {
            val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PatientsVH(binding)
        }

        override fun onBindViewHolder(holder: PatientsVH, position: Int) {
            val list = differ.currentList[position]
            holder.binding.apply {
                tvDoctorName.text = "1 yeni mesaj"
                val doctor = DoctorFeedModel(list, "","","")
                bvQuestion.setOnClickListener {
                    questionClick.onDoctorItemClick(doctor)
                }
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

}