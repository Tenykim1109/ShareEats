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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.src.chat.ChatActivity
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

class ChatListAdapter(val chatUserList: MutableList<ChatList>, val myId: String) : RecyclerView.Adapter<ChatListAdapter.ChatListHolder>(){

    private val TAG = "ChatListAdapter"
    private lateinit var storage: FirebaseStorage
    private val database = FirebaseDatabase.getInstance()
    private val mRef = database.getReference("User")
    var profileUrl = ""
    var nickname = ""

    inner class ChatListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: ChatList) {
            val profile = itemView.findViewById<ImageView>(R.id.iv_chat_list_profile)
            val nickName = itemView.findViewById<TextView>(R.id.tv_chat_list_nickname)
            val recentChat = itemView.findViewById<TextView>(R.id.tv_chat_list_content)
            val recentDate = itemView.findViewById<TextView>(R.id.tv_chat_list_date)

            /* Firebase storage에 닉네임/imageName.확장자 양식으로 저장
            * ex) 애기동열/imgsrc.png */

            storageRef.child("profile").child(profileUrl).downloadUrl.addOnSuccessListener {
                Glide.with(itemView.context).load(it).circleCrop().override(200, 200).into(profile)
            }

//            val storageRef = storage.reference.child(profileUrl)
//
//            storageRef.downloadUrl.addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Glide.with(itemView.context).load(it.result).into(profile)
//                }
//            }

            nickName.text = nickname
            recentChat.text = data.recentChat
            recentDate.text = data.recentDate

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("roomId", data.roomId)
                intent.putExtra("nickname", nickname)
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
        Log.d(TAG, "${chatUserList[position]}")
        setProfile(chatUserList[position], holder, position)

    }

    override fun getItemCount(): Int {
        return chatUserList.size
    }

    private fun setProfile(data: ChatList, holder: ChatListHolder, position: Int) {
        Log.d(TAG, "setProfile called")
        Log.d(TAG, "my id = $myId")
        if (myId == data.userId1) {
            Log.d(TAG, "I'm user1")
            mRef.child(data.userId2).get().addOnSuccessListener {
                val res = it.getValue<UserDto>()!!

                Log.d(TAG, "$res")
                nickname = res.name
                profileUrl = res.profile
                Log.d(TAG, profileUrl)

                holder.bindInfo(chatUserList[position])
            }
        } else {
            Log.d(TAG, "I'm user2")
            mRef.child(data.userId1).get().addOnSuccessListener {
                val res = it.getValue<UserDto>()!!

                nickname = res.name
                profileUrl = res.profile
                Log.d(TAG, profileUrl)

                holder.bindInfo(chatUserList[position])
            }
        }
    }

}