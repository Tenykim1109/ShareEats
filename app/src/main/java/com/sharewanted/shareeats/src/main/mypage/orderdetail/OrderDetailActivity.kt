package com.sharewanted.shareeats.src.main.mypage.orderdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.databaseReference
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.databinding.ActivityOrderDetailBinding
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto

// 주문내역 페이지
class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter
    private var postIdList = mutableListOf<String>()
    private var orderDetailList = mutableListOf<Post>()
    //private var storeList = mutableListOf<Store>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        //getPostId(sharedPreferencesUtil.getUser().id)
    }

    fun initView() {
        adapter = OrderDetailAdapter(orderDetailList)
        binding.fragmentMyPageOrderDetailRv.adapter = adapter
        binding.fragmentMyPageOrderDetailRv.layoutManager = LinearLayoutManager(this)
    }

    // User 테이블에서 PostId 가져오기
    fun getPostId(userId: String) {
        databaseReference
            .child("User").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == User
                    p0.children.forEach {
                        // it == 유저 별 정보

                        // TODO 테이블 이름은 나중에 회의..
                        it.child("orderDetailList").children.forEach {
                            postIdList.add(it.value.toString())
                        }

                        // TODO id로 post 정보 가져오는거 동열님 파트 작업 완료 후 작성
                        getPostInfo()
                    }
                }
            })
    }

    // Post 테이블에서 Post 정보와 StoreId 가져오기
    fun getPostInfo() {
        databaseReference
            .child("Post")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0 == Post
                    p0.children.forEach {
                        // key(id)와 현재 입력한 id가 일치한다면
                        if(postIdList.contains(it.key)) {
                            // TODO id에 해당하는 게시물 정보 가져오기
                            // var post = Post()
                            // it 분기처리해서 정보 뽑고
                            // orderDetailList.add(post)
                            getStoreInfo()
                        }
                    }
                }
            })
    }

    // Store 테이블에서 Store 정보 가져오기
    fun getStoreInfo() {
        for(o in orderDetailList) {
            databaseReference
                .child("Store").child(o.storeId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        // p0 == Post
                        p0.children.forEach {
                            // TODO 스토어 정보 담기
                            // storeList.add() ...
                        }
                    }
                })
        }
    }
}