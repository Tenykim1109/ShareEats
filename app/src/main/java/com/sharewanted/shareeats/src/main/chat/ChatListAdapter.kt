package com.sharewanted.shareeats.src.main.chat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.chat.ChatActivity
import com.sharewanted.shareeats.src.main.chat.models.ChatList

class ChatListAdapter(val chatUserList: MutableList<ChatList>) : RecyclerView.Adapter<ChatListAdapter.ChatListHolder>(){

    private lateinit var storage: FirebaseStorage

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: ChatList) {
            val profile = itemView.findViewById<ImageView>(R.id.iv_chat_list_profile)
            val nickName = itemView.findViewById<TextView>(R.id.tv_chat_list_nickname)
            val recentChat = itemView.findViewById<TextView>(R.id.tv_chat_list_content)
            val recentDate = itemView.findViewById<TextView>(R.id.tv_chat_list_date)

            /* Firebase storage에 닉네임/imageName.확장자 양식으로 저장
            * ex) 애기동열/imgsrc.png */
            Log.d("Chatting...data", "data = $data")
            val profileUrl = data.nickName + "/" + data.imageUrl
            val storageRef = storage.reference.child(profileUrl)

            Log.d("Chatting", "ref = $storageRef")

            storageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(itemView.context).load(it.result).into(profile)
                }
            }

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
        storage = FirebaseStorage.getInstance()
        return ChatListHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListHolder, position: Int) {
        holder.bindInfo(chatUserList[position])
    }

    override fun getItemCount(): Int {
        return 5
    }

//    fun add(data : ChatList) {
//        chatUserList.add(data)
//    }

}