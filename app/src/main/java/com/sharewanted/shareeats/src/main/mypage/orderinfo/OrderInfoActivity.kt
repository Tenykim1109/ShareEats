package com.sharewanted.shareeats.src.main.mypage.orderinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.databinding.ActivityOrderInfoBinding
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.mypage.orderdetail.OrderDetail
import com.sharewanted.shareeats.src.main.mypage.orderdetail.OrderDetailAdapter
import java.text.SimpleDateFormat
import java.util.*

class OrderInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderInfoBinding
    private lateinit var adapter: OrderInfoAdapter
    private val mDatabase = Firebase.database.reference
    private lateinit var postId: String

    private var orderList = mutableListOf<OrderInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getIntExtra("postId", 0).toString()

        initData()
        initView()
    }

    fun initView() {
        adapter = OrderInfoAdapter(orderList)
        binding.activityOrderInfoRvParticipant.adapter = adapter
        binding.activityOrderInfoRvParticipant.layoutManager = LinearLayoutManager(this)

        binding.activityOrderInfoBtnBack.setOnClickListener {
            finish()
        }
    }

    fun initData() {

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.child("Post").child(postId).getValue(Post::class.java)

                binding.activityOrderInfoTvTitle.text = post!!.title
                binding.activityOrderInfoTvWriter.text = post!!.userId

                var date = Date(post.date)
                var closedTime = Date(post.closedTime)
                val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
                binding.activityOrderInfoTvWriteTime.text = timeFormat.format(date).toString()
                binding.activityOrderInfoTvClosedTime.text = timeFormat.format(closedTime).toString()

                val storeName = snapshot.child("Store").child(post.storeId).child("name").value.toString()

                binding.activityOrderInfoTvStore.text = storeName
                binding.activityOrderInfoTvPlace.text = post.place
                binding.activityOrderInfoTvOrderState.text = post.completed
                binding.activityOrderInfoTvContent.text = post.content

                val participantSnapshot = snapshot.child("Post").child(postId).child("participant")

                var total = 0

                for (dataSnapshot in participantSnapshot.children) {
                    val participantId = dataSnapshot.key.toString()

                    val participantProfile = snapshot.child("User").child(participantId).child("profile").value.toString()

                    var addPrice = 0
                    var participantMenu = ""
                    val menuListSnapshot = dataSnapshot.child("menu")

                    Log.d("menuList check", menuListSnapshot.toString())
                    for (menuSnapshot in menuListSnapshot.children) {
                        val menuName = menuSnapshot.child("name").value.toString()
                        val menuPrice = menuSnapshot.child("price").value.toString().toInt()
                        val menuQuantity = menuSnapshot.child("quantity").value.toString().toInt()

                        participantMenu += "${menuName} ${menuQuantity}개\n"
                        addPrice += menuPrice * menuQuantity
                    }
                    total += addPrice
                    val participantPrice = "총 ${CommonUtils().makeComma(addPrice)}"

                    val participantUser = OrderInfo(participantId, participantProfile, participantMenu, participantPrice)
                    orderList.add(participantUser)
                }

                binding.activityOrderInfoRvParticipant.apply {
                    adapter = OrderInfoAdapter(orderList)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                val minPrice = CommonUtils().makeComma(post.minPrice)
                val totalPrice = CommonUtils().makeComma(total)

                binding.activityOrderInfoTvPrice.text = "${totalPrice} / ${minPrice}"

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("OrderInfo db error", error.message)
                Toast.makeText(this@OrderInfoActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}