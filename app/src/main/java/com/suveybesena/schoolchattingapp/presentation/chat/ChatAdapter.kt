package com.suveybesena.schoolchattingapp.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.common.downloadImage
import com.suveybesena.schoolchattingapp.databinding.ItemReceiveMessageBinding
import com.suveybesena.schoolchattingapp.databinding.ItemSentMessageBinding

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2

    private val differCallBack = object : DiffUtil.ItemCallback<FetchedMessageModel>() {
        override fun areItemsTheSame(oldItem: FetchedMessageModel, newItem: FetchedMessageModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FetchedMessageModel, newItem: FetchedMessageModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemSentMessageBinding = ItemSentMessageBinding.bind(itemView)
    }

    inner class ReceiveMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemReceiveMessageBinding = ItemReceiveMessageBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        val chats = differ.currentList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == chats.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sent_message, parent, false)
            SentMessageHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_receive_message, parent, false)
            ReceiveMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chats = differ.currentList[position]
        if (holder.javaClass == SentMessageHolder::class.java) {
            val viewHolder = holder as SentMessageHolder
            if(chats.imageUrl != ""){
                viewHolder.binding.ivMessage.visibility = View.VISIBLE
                viewHolder.binding.ivMessage.downloadImage(chats.imageUrl!!)
            } else {
                viewHolder.binding.ivMessage.visibility = View.GONE
            }
            viewHolder.binding.tvMessage.text = chats.message
        } else {
            val viewHolder = holder as ReceiveMessageHolder
            viewHolder.binding.tvMessage.text = chats.message
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}