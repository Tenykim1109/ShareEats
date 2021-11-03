package com.sharewanted.shareeats.src.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.FragmentChatBinding
import com.sharewanted.shareeats.src.main.chat.models.ChatList


class ChatListFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chat = ChatList("daniel", "hello", "2021-11-02", "https://lh4.googleusercontent.com/-J4s5aAam0Iw/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckKXZR4ZDROtinx4jjprEahHEsy4g/s96-c/photo.jpg")
        val chatList = mutableListOf<ChatList>()
        chatList.add(chat)
        val chatListAdapter = ChatListAdapter(chatList)
        binding.rvChatList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatListAdapter
        }
    }


}