package com.sharewanted.shareeats.src.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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

class HomeAdapter(var postList: MutableList<Post>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val mDatabase = Firebase.database.reference
    private val listSize = postList.size
    private var index = 0

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivStore = itemView.findViewById<ImageView>(R.id.fragment_main_list_item_ivStore)
        var tvTitle = itemView.findViewById<TextView>(R.id.fragment_main_list_item_tvTitle)
        var tvStore = itemView.findViewById<TextView>(R.id.fragment_main_list_item_tvStoreName)
        var tvPrice = itemView.findViewById<TextView>(R.id.fragment_main_list_item_price)
        var progress = itemView.findViewById<LinearProgressIndicator>(R.id.fragment_main_list_item_progress_bar)

        fun onBind(post: Post) {

            mDatabase.child("Store").child(post.storeId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (index < listSize) {
                        val profile = snapshot.child("profile").value.toString()
                        val storeName = snapshot.child("name").value.toString()

                        Glide.with(itemView.context).load(profile).into(ivStore)
                        tvTitle.text = post.title
                        tvStore.text = storeName

                        val menuList = mutableListOf<StoreMenu>()

                        for (dataSnapShot in snapshot.child("menu").children) {
                            val menu = dataSnapShot.getValue(StoreMenu::class.java)
                            menuList.add(menu!!)
                        }

                        Log.d("loop test", "loop check")

                        mDatabase.child("Post").child(post.postId.toString()).child("participant")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var totalPrice = 0
                                    for (dataSnapshot in snapshot.children) {
                                        Log.d("data test", dataSnapshot.value.toString())

                                        for (index in menuList.indices) {
                                            val price = dataSnapshot.child("menu").child(menuList[index].name).child("price").value.toString().toInt()
                                            val quantity = dataSnapshot.child("menu")
                                                .child(menuList[index].name)
                                                .child("quantity").value.toString().toInt()

                                            if (price != null) {
                                                totalPrice += price * quantity
                                            }
                                        }

                                    }

                                    val goal = "${CommonUtils().makeComma(totalPrice)} / ${
                                        CommonUtils().makeComma(post.minPrice)
                                    }"
                                    tvPrice.text = goal

                                    progress.max = post.minPrice
                                    progress.progress = totalPrice


                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("HomeAdapter Post error", error.toString())
                                    Toast.makeText(itemView.context, error.toString(), Toast.LENGTH_SHORT).show()
                                }

                            })
                        refreshPost()
                        index++
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("HomeAdapter Store error", error.toString())
                    Toast.makeText(itemView.context, error.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_list_item_menu, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.onBind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    private fun refreshPost() {
        notifyDataSetChanged()
    }

}