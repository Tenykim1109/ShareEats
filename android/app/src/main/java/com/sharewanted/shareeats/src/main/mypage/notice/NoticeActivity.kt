package com.sharewanted.shareeats.src.main.mypage.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.databinding.ActivityCreatorBinding
import com.sharewanted.shareeats.databinding.ActivityNoticeBinding
import com.sharewanted.shareeats.src.main.mypage.creator.Creator
import com.sharewanted.shareeats.src.main.mypage.creator.CreatorAdapter

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNoticeBinding
    private lateinit var adapter: NoticeAdapter
    private var list = mutableListOf<Notice>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()

        adapter.setItemClickListener(object: NoticeAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@NoticeActivity, NoticeContentActivity::class.java).apply {
                    putExtra("notice", list[position])
                }

                startActivity(intent)
            }
        })
    }

    fun initView() {
        adapter = NoticeAdapter(list)
        binding.fragmentMyPageNoticeRv.adapter = adapter
        binding.fragmentMyPageNoticeRv.layoutManager = LinearLayoutManager(this)

        binding.fragmentMyPageNoticeIvArrow.setOnClickListener {
            finish()
        }
    }

    fun initData() {

        list.add(Notice("개인정보처리방침 일부 변경 안내", "2021.10.31", "안녕하세요. 쉐어이츠 입니다.\n" +
                "항상 저희 쉐어이츠를 이용해주시는 고객 여러분께 깊이 감사 드립니다.\n" +
                "2021년 11월 1일부터 쉐어이츠 개인정보 처리방침이 일부 변경됩니다.\n" +
                "■ 개인정보 처리방침 개정 사항\n" +
                "\n" +
                "1. 개인정보 처리방침 개정 목적\n" +
                "– 사명 변경, 수집, 위탁 및 제공 등 변경사항에 대한 최신화 등\n" +
                "\n" +
                "2. 개인정보 처리방침 고지 및 시행일자\n" +
                "– 고지일자 : 2021.10.31\n" +
                "– 시행일자 : 2021.11.01\n" +
                "\n" +
                "3. 개인정보 처리방침 주요 개정 내용\n" +
                "\n" +
                "1) 제1조 개인정보의 수집 ∙ 이용\n" +
                "\n" +
                "– 본인인증 시 수집항목 “CI(연계정보)” 추가\n" +
                "\n" +
                "2) 제3조 개인정보처리의 위탁\n" +
                "\n" +
                "3) 제4조 개인정보 제3자 제공 및 공유\n" +
                "\n" +
                "고객님들께 더 좋은 서비스를 제공하는 쉐어이츠가 되겠습니다."))
        list.add(Notice("쉐어이츠 시스템 점검 안내", "2021.10.29", "안녕하세요. 쉐어이츠입니다.\n항상 저희 쉐어이츠를 이용해주시는 고객 여러분께 깊이 감사 드립니다.\n더 나은 쉐어이츠 서비스 제공을 위해 시스템울 점검할 예정입니다." +
                "\n\n아래와 같이 쉐어이츠 서비스가 일시 중단되오니 서비스 이용에 참고하시기 바랍니다.\n\n작업 일시: 2021년 10월 30일 (토) 새벽 1:00 ~ 6:30 (약 5시간 30분)\n작업 영향: 쉐어이츠 서비스 중단\n점검 소요 시간은 작업 상황에 따라 변경될 수 있습니다." +
                "\n\n더 좋은 서비스를 제공하도록 항상 노력하겠으며, 고객님의 양해 부탁드립니다." ))
    }
}