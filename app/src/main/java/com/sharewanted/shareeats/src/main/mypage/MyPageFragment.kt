package com.sharewanted.shareeats.src.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.FragmentMyPageBinding
import com.sharewanted.shareeats.src.main.mypage.creator.CreatorActivity

class MyPageFragment : Fragment(), MyPageMenuClickListener {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var adapter: MyPageMenuAdapter
    private var list = mutableListOf<String>("주문 내역", "회원정보 수정", "공지사항", "만든이")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    fun initView() {
        adapter = MyPageMenuAdapter(list, this)
        binding.fragmentMyPageRvMenu.adapter = adapter
        binding.fragmentMyPageRvMenu.layoutManager = LinearLayoutManager(context)
    }

    override fun onClick(position: Int) {

        when(position) {
            3 -> { // 만든이 페이지 이동
                val intent = Intent(requireContext(), CreatorActivity::class.java)
                startActivity(intent)
            }
        }

        /*
        when(position) {
            0 -> startActvity(Intent(this, xxxActivity::class.java))
        }

         */
    }
}