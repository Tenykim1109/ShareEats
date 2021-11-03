package com.sharewanted.shareeats.src.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityChatBinding
import com.sharewanted.shareeats.src.main.chat.ChatAdapter
import com.sharewanted.shareeats.src.main.chat.models.Chat

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Chat("hong", "안녕하세요.", "https://lh4.googleusercontent.com/-J4s5aAam0Iw/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckKXZR4ZDROtinx4jjprEahHEsy4g/s96-c/photo.jpg", true)
        val other = Chat("Lee", "반갑습니다.", "https://lh4.googleusercontent.com/-J4s5aAam0Iw/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckKXZR4ZDROtinx4jjprEahHEsy4g/s96-c/photo.jpg", false)

        val list = mutableListOf<Chat>()

        list.add(user)
        list.add(other)

        val chatAdapter = ChatAdapter(list)

        binding.rvChatView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatAdapter
        }



    }
}