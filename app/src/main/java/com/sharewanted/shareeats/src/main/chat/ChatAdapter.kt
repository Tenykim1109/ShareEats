package com.sharewanted.shareeats.src.main.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.chat.models.Chat

class ChatAdapter(val chatList: MutableList<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindInfo(data: Chat) {
            val otherChatFrame = itemView.findViewById<ConstraintLayout>(R.id.cl_chat_other_layout)
            val userChatFrame = itemView.findViewById<ConstraintLayout>(R.id.cl_chat_layout)

            if (data.auth == true) {
                otherChatFrame.visibility = View.VISIBLE
                userChatFrame.visibility = View.GONE

                val otherProfile = itemView.findViewById<ImageView>(R.id.iv_chat_other_profile)
                val otherName = itemView.findViewById<TextView>(R.id.tv_chat_other_nickName)
                val otherChat = itemView.findViewById<TextView>(R.id.tv_chat_message_other)

                Log.d("data check", data.nickName)

                Glide.with(itemView.context).load(data.imageUrl).placeholder(R.drawable.ic_navi_mypage).apply(RequestOptions().circleCrop().circleCrop()).into(otherProfile)
                otherName.text = data.nickName
                otherChat.text = data.content

            } else {
                userChatFrame.visibility = View.VISIBLE
                otherChatFrame.visibility = View.GONE

                val userProfile = itemView.findViewById<ImageView>(R.id.iv_chat_profile)
                val userName = itemView.findViewById<TextView>(R.id.tv_chat_nickName)
                val userChat = itemView.findViewById<TextView>(R.id.tv_chat_message)

                Log.d("data check", data.nickName)

                Glide.with(itemView.context).load(data.imageUrl).placeholder(R.drawable.ic_navi_mypage).apply(RequestOptions().circleCrop().circleCrop()).into(userProfile)
                userName.text = data.nickName
                userChat.text = data.content
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bindInfo(chatList[position])
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}