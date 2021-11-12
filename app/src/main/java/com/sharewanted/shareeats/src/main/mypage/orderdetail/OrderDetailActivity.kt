package com.sharewanted.shareeats.src.main.mypage.orderdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sharewanted.shareeats.config.ApplicationClass.Companion.databaseReference
import com.sharewanted.shareeats.config.ApplicationClass.Companion.sharedPreferencesUtil
import com.sharewanted.shareeats.databinding.ActivityOrderDetailBinding
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.orderDto.Store
import com.sharewanted.shareeats.src.main.mypage.orderinfo.OrderInfoActivity

// 주문내역 페이지
class OrderDetailActivity : AppCompatActivity(), OrderDetailClickListener {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter
    private var postIdList = mutableListOf<String>()
    private var postList = mutableListOf<Post>()
    private var storeList = mutableListOf<Store>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        getPostId(sharedPreferencesUtil.getUser().id)
    }

    fun initView() {
        adapter = OrderDetailAdapter(postList, storeList, this)
        binding.fragmentMyPageOrderDetailRv.adapter = adapter
        binding.fragmentMyPageOrderDetailRv.layoutManager = LinearLayoutManager(this)
    }

    // User 테이블에서 PostId 가져오기
    fun getPostId(userId: String) {
        databaseReference
            .child("User").child(userId).child("postList")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    // p0: postId 목록
                    p0.children.forEach {
                        postIdList.add(it.key.toString())
                    }
                    getPostInfo()
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
                    // p0
                    for (id in postIdList) {
                        var postId = p0.child(id).child("postId").value.toString()
                        var title = p0.child(id).child("title").value.toString()
                        var date = p0.child(id).child("date").value.toString()
                        var storeId = p0.child(id).child("storeId").value.toString()
                        var place = p0.child(id).child("place").value.toString()
                        var fund = p0.child(id).child("fund").value.toString()
                        var minPrice = p0.child(id).child("minPrice").value.toString()

                        var post = Post(
                            postId.toInt(), title, date.toLong(), "", storeId, place,
                            0, "", fund.toInt(), minPrice.toInt(), "", ""
                        )
                        postList.add(post)
                    }

                    getStoreInfo()
                }
            })
    }

    // Store 테이블에서 Store 정보 가져오기
    fun getStoreInfo() {
        databaseReference
            .child("Store")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (p in postList) {
                        var name = p0.child(p.storeId).child("name").value.toString()
                        var profile = p0.child(p.storeId).child("profile").value.toString()

                        var store = Store("", name, profile, "", "", "", "", mutableListOf(), 0)
                        storeList.add(store)
                    }

                    adapter.notifyDataSetChanged()
                }
            })
    }

    override fun onClick(postId: Int) {
        var intent = Intent(this, OrderInfoActivity::class.java)
        intent.putExtra("postId", postId)
        startActivity(intent)
    }
}