package com.sharewanted.shareeats.src.main.home.postInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.databinding.ActivityPostInfoBinding
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu
import com.sharewanted.shareeats.src.main.home.order.selectMenu.SelectMenuActivity
import java.text.SimpleDateFormat
import java.util.*

class PostInfoActivity : AppCompatActivity() {
    private lateinit var postId: String
    private var selectedMenuList = mutableListOf<StoreMenu>()
    private val mDatabase = Firebase.database.reference

    private lateinit var binding: ActivityPostInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getIntExtra("postId", 0).toString()

    }

    override fun onStart() {
        super.onStart()

        binding.activityPostInfoBtnCancel.setOnClickListener {
            finish()
        }

        mDatabase.child("Post").child(postId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(postSnapshot: DataSnapshot) {
                val post = postSnapshot.getValue(Post::class.java)

                mDatabase.child("Store").child(post!!.storeId).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val storeName = snapshot.child("name").getValue(String::class.java)

                        binding.activityPostInfoTvTitle.text = post!!.title
                        binding.activityPostInfoTvWriter.text = post!!.userId
                        var date = Date(post.date)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                        binding.activityPostInfoTvWriteDate.text = dateFormat.format(date).toString()
                        binding.activityPostInfoTvLocation.text = post!!.place
                        date = Date(post.closedTime)
                        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
                        binding.activityPostInfoTvClosedTime.text = timeFormat.format(date).toString()
                        binding.activityPostInfoTvContent.text = post!!.content

                        binding.activityPostInfoTvStore.text = storeName

                        binding.activityPostInfoBtnSelectMenu.setOnClickListener {

                            val intent = Intent(this@PostInfoActivity, SelectMenuActivity::class.java).apply {
                                putExtra("storeId", post!!.storeId)
                            }
                            activityResult.launch(intent)

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@PostInfoActivity, error.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PostInfoActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })


    }


    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {

            val menuList = it.data?.getParcelableArrayListExtra<StoreMenu>("menuList")
            val quantityList = it.data?.getIntegerArrayListExtra("menuQuantityList")
            var menuString = ""
            for (i in quantityList!!.indices) {
                if (quantityList[i] != 0) {
                    val storeMenu = menuList?.get(i)?.let { it1 -> StoreMenu(menuList[i].name, menuList[i].price, menuList[i].photo, it1.desc, quantityList[i]) }
                    selectedMenuList.add(storeMenu!!)
                    menuString += "${storeMenu.name} ${storeMenu.quantity}개 \n"
                }
            }
            binding.activityPostInfoTvMenu.text = menuString

        }
    }
}