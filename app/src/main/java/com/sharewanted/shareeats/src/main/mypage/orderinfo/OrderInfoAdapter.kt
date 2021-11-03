package com.sharewanted.shareeats.src.main.mypage.orderinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class OrderInfoAdapter(var list: MutableList<OrderInfo>) : RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    inner class OrderInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_order_info_profile = itemView.findViewById<ImageView>(R.id.fragment_my_page_order_info_profile)
        var fragment_my_page_order_info_name = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_name)
        var fragment_my_page_order_info_orders = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_orders)
        var fragment_my_page_order_info_price = itemView.findViewById<TextView>(R.id.fragment_my_page_order_info_price)

        fun onBind(o: OrderInfo) {

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