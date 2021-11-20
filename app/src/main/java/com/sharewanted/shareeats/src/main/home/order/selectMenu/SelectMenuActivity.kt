package com.sharewanted.shareeats.src.main.home.order.selectMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivitySelectMenuBinding
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu

class SelectMenuActivity : AppCompatActivity() {

    private val mDatabase = Firebase.database.reference.child("Store")

    private lateinit var binding: ActivitySelectMenuBinding
    private var menuList = arrayListOf<StoreMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activitySelectMenuBtnCancel.setOnClickListener {
            finish()
        }

        val userType = intent.getStringExtra("userType").toString()
        val storeId = intent.getStringExtra("storeId").toString()
        val storeMinPrice = intent.getIntExtra("storeMinPrice", 0)


        mDatabase.child(storeId).child("menu").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = arrayListOf<StoreMenu>()

                for (menu in snapshot.children) {
                    val menuName = menu.child("name").getValue(String::class.java)!!
                    val menuPhoto = menu.child("photo").getValue(String::class.java)!!
                    val menuPrice = menu.child("price").getValue(Int::class.java)!!
                    val menuDesc = menu.child("desc").getValue(String::class.java)!!

                    list.add(StoreMenu(menuName, menuPrice, menuPhoto, menuDesc))
                }

                val menuAdapter = SelectMenuAdapter(list)

                var quantityArr = arrayListOf<Int>()

                binding.activitySelectMenuRv.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = menuAdapter
                }

                binding.activitySelectMenuSave.setOnClickListener {
                    quantityArr.clear()

                    quantityArr.addAll(menuAdapter.quantityArr)

                    var totalMenuPrice = 0
                    for (i in list.indices) {
                        totalMenuPrice += list[i].price * quantityArr[i]
                    }

                    Log.d("price check", "minPrice : ${storeMinPrice}, totalPrice: ${totalMenuPrice}")

                    if (userType == "writer") {
                        if (totalMenuPrice == 0) {
                            Toast.makeText(this@SelectMenuActivity, "메뉴를 최소 1개 이상 선택하세요.", Toast.LENGTH_SHORT).show()
                        } else if (storeMinPrice <= totalMenuPrice) {
                            Toast.makeText(this@SelectMenuActivity, "매장 주문 최소금액을 넘을 수 없습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(this@SelectMenuActivity, OrderActivity::class.java).apply {
                                putExtra("resultType", "selectMenu")
                                putParcelableArrayListExtra("menuList", menuAdapter.menuList as ArrayList<StoreMenu>)
                                putIntegerArrayListExtra("menuQuantityList", quantityArr)
                            }
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    } else {
                        if (totalMenuPrice == 0) {
                            Toast.makeText(this@SelectMenuActivity, "메뉴를 최소 1개 이상 선택하세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(this@SelectMenuActivity, OrderActivity::class.java).apply {
                                putExtra("resultType", "selectMenu")
                                putParcelableArrayListExtra("menuList", menuAdapter.menuList as ArrayList<StoreMenu>)
                                putIntegerArrayListExtra("menuQuantityList", quantityArr)
                            }
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SelectMenuActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }
}