package com.sharewanted.shareeats.src.main.mypage.orderdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.orderDto.Store
import java.security.Timestamp
import java.text.SimpleDateFormat

class OrderDetailAdapter(var postList: MutableList<Post>, var storeList: MutableList<Store>, var listener: OrderDetailClickListener) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {
    var pattern = "yyyy.mm.dd"
    var formatter = SimpleDateFormat(pattern)

    inner class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_order_detail_list_item_layout = itemView.findViewById<ConstraintLayout>(R.id.fragment_my_page_order_detail_list_item_layout)
        var fragment_my_page_order_detail_list_item_ivStore = itemView.findViewById<ImageView>(R.id.fragment_my_page_order_detail_list_item_ivStore)
        var fragment_my_page_order_detail_list_item_tvTitle = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvTitle)
        var fragment_my_page_order_detail_list_item_tvStoreName = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvStoreName)
        var fragment_my_page_order_detail_list_item_tvDate = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvDate)
        var fragment_my_page_order_detail_list_item_price = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_price)

        fun onBind(p: Post, s: Store) {
            Glide.with(itemView.context).load(s.profile).circleCrop().override(80, 80).into(fragment_my_page_order_detail_list_item_ivStore)
            fragment_my_page_order_detail_list_item_tvTitle.text = p.title
            fragment_my_page_order_detail_list_item_tvStoreName.text = s.name
            fragment_my_page_order_detail_list_item_tvDate.text = formatter.format(p.date).toString()
            fragment_my_page_order_detail_list_item_price.text = "${CommonUtils().makeComma(p.fund)}원/${CommonUtils().makeComma(p.minPrice)}원"

            fragment_my_page_order_detail_list_item_layout.setOnClickListener {
                listener.onClick(p.postId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_order_detail, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.OrderDetailViewHolder, position: Int) {
        holder.onBind(postList[position], storeList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}