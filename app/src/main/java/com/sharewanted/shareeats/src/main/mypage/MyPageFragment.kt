package com.sharewanted.shareeats.src.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.databinding.FragmentMyPageBinding
import com.sharewanted.shareeats.src.main.mypage.creator.CreatorActivity
import com.sharewanted.shareeats.src.main.mypage.edituser.EditUserActivity
import com.sharewanted.shareeats.src.main.mypage.notice.NoticeActivity
import com.sharewanted.shareeats.src.main.mypage.orderdetail.OrderDetailActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

class MyPageFragment : Fragment(), MyPageMenuClickListener {
    private lateinit var binding: FragmentMyPageBinding
    private lateinit var adapter: MyPageMenuAdapter
    private var list = mutableListOf<String>("주문 내역", "회원정보 수정", "공지사항", "만든이")
    lateinit var user: UserDto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = ApplicationClass.sharedPreferencesUtil.getUser()

        initView()

        binding.fragmentMyPageTvName.text = user.name
        storageRef.child("profile").child(user.profile).downloadUrl.addOnSuccessListener {
            Glide.with(this)
                .load(it)
                .circleCrop()
                .into(binding.fragmentMyPageIvProfile)
        }
    }

    fun initView() {
        adapter = MyPageMenuAdapter(list, this)
        binding.fragmentMyPageRvMenu.adapter = adapter
        binding.fragmentMyPageRvMenu.layoutManager = LinearLayoutManager(context)
    }

    override fun onClick(position: Int) {
        when(position) {
            0 -> startActivity(Intent(requireContext(), OrderDetailActivity::class.java))
            1 -> startActivity(Intent(requireContext(), EditUserActivity::class.java))
            2 -> startActivity(Intent(requireContext(), NoticeActivity::class.java))
            3 -> startActivity(Intent(requireContext(), CreatorActivity::class.java))
        }
    }
}