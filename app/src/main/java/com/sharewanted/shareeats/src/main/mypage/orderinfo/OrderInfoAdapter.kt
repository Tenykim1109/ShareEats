package com.sharewanted.shareeats.src.main.mypage.orderinfo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.ApplicationClass.Companion.storageRef
import com.sharewanted.shareeats.src.chat.ChatActivity

class OrderInfoAdapter(var list: MutableList<OrderInfo>) : RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    inner class OrderInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_order_info_profile = itemView.findViewById<ImageView>(R.id.fragment_my_page_order_info_profile)
        var fragment_my_page_order_info_name = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_name)
        var fragment_my_page_order_info_orders = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_orders)
        var fragment_my_page_order_info_price = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_price)

        fun onBind(order: OrderInfo) {

            storageRef.child("profile").child(order.profile).downloadUrl.addOnSuccessListener {
                Glide.with(itemView.context).load(it).circleCrop().override(200, 200).into(fragment_my_page_order_info_profile)
            }
            fragment_my_page_order_info_profile.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                itemView.context.startActivity(intent)
            }
            fragment_my_page_order_info_name.text = order.userId
            fragment_my_page_order_info_orders.text = order.menu
            fragment_my_page_order_info_price.text = order.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInfoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_order_info, parent, false)
        return OrderInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderInfoAdapter.OrderInfoViewHolder, position: Int) {
            holder.onBind(list[position])
        }

    override fun getItemCount(): Int {
        return list.size
    }
}