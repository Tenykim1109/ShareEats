package com.sharewanted.shareeats.src.main.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.sharewanted.shareeats.databinding.FragmentChatBinding
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import com.sharewanted.shareeats.util.SharedPreferencesUtil


class ChatListFragment : Fragment() {
    private val TAG = "ChatListFragment"
    private lateinit var binding: FragmentChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var mRef: DatabaseReference
    private lateinit var chatList: MutableList<ChatList>
    private lateinit var chatListAdapter: ChatListAdapter
    private lateinit var childEventListener: ChildEventListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()

        chatList = mutableListOf()
        mRef = database.getReference("Chat")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myId = SharedPreferencesUtil(requireContext()).getUser()
        Log.d(TAG, myId.id)

        chatListAdapter = ChatListAdapter(chatList, myId.id)
        binding.rvChatList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatListAdapter
        }

        getChatting(myId.id)
    }

    private fun getChatting(myId: String) {
        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "$snapshot")
                val userId1 = snapshot.child("userId1").getValue<String>()!!
                val userId2 = snapshot.child("userId2").getValue<String>()!!
                val recentChat = snapshot.child("recentChat").getValue<String>()!!
                val recentDate = snapshot.child("recentDate").getValue<String>()!!

                if (myId == userId1 || myId == userId2) {
                    Log.d("Chatting...roomId", "${snapshot.key!!}")
                    chatList.add(ChatList(snapshot.key!!, userId1, userId2, recentChat, recentDate))
                    chatListAdapter.notifyDataSetChanged()
                }
                binding.rvChatList.smoothScrollToPosition(chatList.size)

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("ChatActivity_get", "변경")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d("ChatActivity_get", "삭제")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("ChatActivity_get", "이동")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ChatActivity_get", "취소")
            }

        }

        mRef.addChildEventListener(childEventListener)
    }
}