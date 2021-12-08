package com.sharewanted.shareeats.src.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityChatBinding
import com.sharewanted.shareeats.src.main.chat.ChatAdapter
import com.sharewanted.shareeats.src.main.chat.ChatListAdapter
import com.sharewanted.shareeats.src.main.chat.models.Chat
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import com.sharewanted.shareeats.util.SharedPreferencesUtil
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.String.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var mRef: DatabaseReference
    private lateinit var childEventListener: ChildEventListener
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var list: MutableList<Chat>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomId = intent.getStringExtra("roomId")!!
        val myNick = SharedPreferencesUtil(this).getUser().name
        val nickname = intent.getStringExtra("nickname")!!
        val myId = SharedPreferencesUtil(this).getUser().id
        val myImage = SharedPreferencesUtil(this).getUser().profile
        Log.d("ChatActivity_roomId", roomId)

        list = mutableListOf()

        chatAdapter = ChatAdapter(list, myId)

        // Firebase reference
        database = FirebaseDatabase.getInstance()
        mRef = database.getReference("Chat").child(roomId).child("Chat")

        binding.rvChatView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatAdapter
        }


        binding.btnChatSend.setOnClickListener {
            val msg = binding.editTextTextPersonName.text.toString()

            val current = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

            if (!TextUtils.isEmpty(msg)) {
                binding.editTextTextPersonName.setText("")
                mRef.push().setValue(Chat(myId, msg, myImage, current.format(formatter), myNick))
                database.getReference("Chat").child(roomId).child("recentChat").setValue(msg)
                database.getReference("Chat").child(roomId).child("recentDate").setValue(current.format(formatter))
            }
        }

        getChatting()
    }

    private fun getChatting() {
        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chat = snapshot.getValue(Chat::class.java)!!
                list.add(chat)
                chatAdapter.notifyDataSetChanged()
                binding.rvChatView.smoothScrollToPosition(list.size)
                Log.d("ChatActivity_get", "$chat")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                chatAdapter.notifyDataSetChanged()
                Log.d("ChatActivity_get", "변경")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                chatAdapter.notifyDataSetChanged()
                Log.d("ChatActivity_get", "삭제")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                chatAdapter.notifyDataSetChanged()
                Log.d("ChatActivity_get", "이동")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatActivity_get", "취소")
            }

        }

        mRef.addChildEventListener(childEventListener)
    }
}
