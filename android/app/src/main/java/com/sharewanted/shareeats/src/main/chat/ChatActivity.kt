package com.sharewanted.shareeats.src.main.chat

import android.content.Intent
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
import com.sharewanted.shareeats.config.ApplicationClass
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

private const val TAG = "ChatActivity_싸피"
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

        val myNick = SharedPreferencesUtil(this).getUser().name
        val writer = intent.getStringExtra("writer")
        val myId = SharedPreferencesUtil(this).getUser().id
        val myImage = SharedPreferencesUtil(this).getUser().profile
        var roomId = intent.getStringExtra("roomId")

        list = mutableListOf()

        chatAdapter = ChatAdapter(list, myId)

        // Firebase reference
        database = FirebaseDatabase.getInstance()
        val chatRef = database.getReference("Chat")

        // ChatActivity 내부에서 과거 채팅 기록이 있는지 확인하여 data init
        chatRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (roomId == null) {
                    Log.d(TAG, "새방판다")

                    val newRef = chatRef.push()
                    roomId = newRef.key!!
                    database.getReference("Chat").child(roomId!!).child("userId1").setValue(ApplicationClass.sharedPreferencesUtil.getUser().id)
                    database.getReference("Chat").child(roomId!!).child("userId2").setValue(writer)

                } else {
                    Log.d(TAG, roomId!!)
                }

                mRef = database.getReference("Chat").child(roomId!!).child("Chat")
                Log.d(TAG, "EventLister ended.")
                getChatting()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error occurred.")
            }
        })

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
                database.getReference("Chat").child(roomId!!).child("recentChat").setValue(msg)
                database.getReference("Chat").child(roomId!!).child("recentDate").setValue(current.format(formatter))
            }
        }

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
