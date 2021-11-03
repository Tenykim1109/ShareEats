package com.sharewanted.shareeats.src.main.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.chat.ChatActivity
import com.sharewanted.shareeats.src.main.chat.models.ChatList

class ChatListAdapter(val chatUserList: MutableList<ChatList>) : RecyclerView.Adapter<ChatListAdapter.ChatListHolder>(){

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindInfo(data: ChatList) {
            val profile = itemView.findViewById<ImageView>(R.id.iv_chat_list_profile)
            val nickName = itemView.findViewById<TextView>(R.id.tv_chat_list_nickname)
            val recentChat = itemView.findViewById<TextView>(R.id.tv_chat_list_content)
            val recentDate = itemView.findViewById<TextView>(R.id.tv_chat_list_date)

            val profileUrl = data.imageUrl
            Glide.with(itemView.context).load(profileUrl).apply(RequestOptions().circleCrop().circleCrop()).into(profile)
            nickName.text = data.nickName
            recentChat.text = data.recentChat
            recentDate.text = data.recentDate

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ChatListHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListHolder, position: Int) {
        holder.bindInfo(chatUserList[position])
    }

    override fun getItemCount(): Int {
        return 5
    }

}