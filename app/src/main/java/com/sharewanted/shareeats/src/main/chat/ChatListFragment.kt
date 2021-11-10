package com.sharewanted.shareeats.src.main.chat

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.sharewanted.shareeats.databinding.FragmentChatBinding
import com.sharewanted.shareeats.src.main.chat.models.Chat
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import com.sharewanted.shareeats.util.SharedPreferencesUtil
import kotlinx.coroutines.*


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
        mRef = database.getReference("Chatting")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatListAdapter = ChatListAdapter(chatList, "애기동열")
        binding.rvChatList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatListAdapter
        }

        val myNickname = SharedPreferencesUtil(requireContext()).getUser()
        Log.d(TAG, myNickname.name)

        getChatting("애기동열")
    }

    private fun getChatting(myNickname: String) {
        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val nickName1 = snapshot.child("nickName1").getValue<String>()!!
                val nickName2 = snapshot.child("nickName2").getValue<String>()!!
                val imgUrl1 = snapshot.child("imgUrl1").getValue<String>()!!
                val imgUrl2 = snapshot.child("imgUrl2").getValue<String>()!!
                val recentChat = snapshot.child("recentChat").getValue<String>()!!
                val recentDate = snapshot.child("recentDate").getValue<String>()!!

                if (myNickname == nickName1 || myNickname == nickName2) {
                    Log.d("Chatting...roomId", "${snapshot.key!!}")
                    chatList.add(ChatList(snapshot.key!!, nickName1, nickName2, recentChat, recentDate, imgUrl1, imgUrl2))
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