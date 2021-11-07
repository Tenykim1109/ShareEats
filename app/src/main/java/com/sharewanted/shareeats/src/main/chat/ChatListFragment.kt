package com.sharewanted.shareeats.src.main.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.FragmentChatBinding
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import kotlinx.coroutines.*


class ChatListFragment : Fragment() {

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
//        chatList.add(ChatList("ASDF", "ASDF", "2021.11.01", "ASDF"))
//        initFirebaseDatabase()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Default).async {
                initFirebaseDatabase()
            }.await()

            // Firebase에서 채팅방 목록 불러오는 동안 Main thread delay
            delay(1500L)
            for (data in chatList) {
                Log.d("Chatting...333", "$data")
            }

            chatListAdapter = ChatListAdapter(chatList)
            binding.rvChatList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = chatListAdapter
            }
        }
    }

    private suspend fun initFirebaseDatabase() = coroutineScope {
        launch {
            mRef = database.getReference("Chatting")

            mRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child1 in snapshot.children) {
                        for (child2 in child1.children) {
                            val asdf = child2.getValue<ChatList>()
                            Log.d("Chatting...111", "value = $asdf")
                            chatList.add(asdf!!)
//                        chatListAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error", "Failed to read value.")
                }
            })
        }
    }
}