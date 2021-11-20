package com.sharewanted.shareeats.src.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu
import com.sharewanted.shareeats.src.main.home.postInfo.PostInfoActivity

class HomeListAdapter(private var postList: MutableList<Post>) : BaseAdapter() {

    private val mDatabase = Firebase.database.reference
    private var index = 0

    override fun getCount(): Int {
        return postList.size
    }

    override fun getItem(position: Int): Any {
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_main_list_item_menu, parent, false)
        }
        val post = postList[position]
        var ivStore = convertView!!.findViewById<ImageView>(R.id.fragment_main_list_item_ivStore)
        var tvTitle = convertView.findViewById<TextView>(R.id.fragment_main_list_item_tvTitle)
        var tvStore = convertView.findViewById<TextView>(R.id.fragment_main_list_item_tvStoreName)
        var tvPrice = convertView.findViewById<TextView>(R.id.fragment_main_list_item_price)
        var progress = convertView.findViewById<LinearProgressIndicator>(R.id.fragment_main_list_item_progress_bar)

        mDatabase.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                if (index < postList.size) {
                    val profile = snapshot.child("Store").child(post.storeId).child("profile").value.toString()
                    val storeName = snapshot.child("Store").child(post.storeId).child("name").value.toString()

                    val menuList = mutableListOf<StoreMenu>()

                    for (dataSnapShot in snapshot.child("Store").child(post.storeId).child("menu").children) {
                        val menu = dataSnapShot.getValue(StoreMenu::class.java)
                        menuList.add(menu!!)
                    }

                    Log.d("loop test", "loop check")

                Glide.with(convertView.context).load(profile).into(ivStore)
                tvTitle.text = post.title
                tvStore.text = storeName

                val postSnapshot = snapshot.child("Post").child(post.postId.toString()).child("participant")


                var totalPrice = 0
                for (dataSnapshot in postSnapshot.children) {
                    Log.d("data test", dataSnapshot.value.toString())

                    for (index in menuList.indices) {
                        val price = dataSnapshot.child("menu")
                            .child(menuList[index].name)
                            .child("price").value.toString()
                        val quantity = dataSnapshot.child("menu")
                            .child(menuList[index].name)
                            .child("quantity").value.toString()

                        if (price != "null") {
                            totalPrice += price.toInt() * quantity.toInt()
                        }
                    }

                    val goal = "${CommonUtils().makeComma(totalPrice)} / ${
                        CommonUtils().makeComma(post.minPrice)
                    }"
                    tvPrice.text = goal

                    progress.max = post.minPrice
                    progress.progress = totalPrice

                }
                    refreshPost()
//                    index++
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("HomeAdapter Store error", error.toString())
                Toast.makeText(convertView.context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        return convertView
    }

    private fun refreshPost() {
        notifyDataSetChanged()
    }
}