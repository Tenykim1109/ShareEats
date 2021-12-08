package com.sharewanted.shareeats.src.main.home.order.findStore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityFindStoreBinding
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Store
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu

class FindStoreActivity : AppCompatActivity() {
    private val mDatabase = Firebase.database.reference.child("Store")

    private lateinit var binding : ActivityFindStoreBinding
    private val storeList = mutableListOf<Store>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityFindStoreBtnCancel.setOnClickListener {
            finish()
        }

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val list = mutableListOf<StoreMenu>()

                    for (menu in snapshot.child("menu").children) {
                        val menuName = menu.child("name").getValue(String::class.java)!!
                        val menuPhoto = menu.child("photo").getValue(String::class.java)!!
                        val menuPrice = menu.child("price").getValue(Int::class.java)!!
                        val menuDesc = menu.child("desc").getValue(String::class.java)!!

                        list.add(StoreMenu(menuName, menuPrice, menuPhoto, menuDesc))
                    }

                    Log.d("value check", list.toString())
                    val store = Store(data.child("storeId").getValue(String::class.java)!!, data.child("name").getValue(String::class.java)!!,
                        data.child("profile").getValue(String::class.java)!!, data.child("location").getValue(String::class.java)!!,
                        data.child("tel").getValue(String::class.java)!!, data.child("time").getValue(String::class.java)!!,
                        data.child("info").getValue(String::class.java)!!, list, data.child("minPrice").getValue(Int::class.java)!!,
                        data.child("type").getValue(String::class.java)!!)
                    storeList.add(store)
                }

                val storeAdapter = FindStoreAdapter(storeList)
                binding.activityFindStoreRv.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = storeAdapter
                }

                val autoCompleteAdapter = AutoCompleteAdapter(this@FindStoreActivity, R.layout.activity_find_store_list_item, storeList)
                binding.activityFindStoreActv.setAdapter(autoCompleteAdapter)
                binding.activityFindStoreActv.setOnItemClickListener { adapterView, view, i, l ->
                    val selectedStore = storeList[i]
                    val intent = Intent(this@FindStoreActivity, OrderActivity::class.java).apply {
                        putExtra("resultType", "findStore")
                        putExtra("storeId", selectedStore.storeId)
                        putExtra("storeName", selectedStore.name)
                        putExtra("storeMinPrice", selectedStore.minPrice.toString())
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FindStoreActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }
}