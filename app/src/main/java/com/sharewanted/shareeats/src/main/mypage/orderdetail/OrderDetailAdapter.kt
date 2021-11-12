package com.sharewanted.shareeats.src.main.mypage.orderdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post

class OrderDetailAdapter(var list: MutableList<Post>) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    inner class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_order_detail_list_item_ivStore = itemView.findViewById<ImageView>(R.id.fragment_my_page_order_detail_list_item_ivStore)
        var fragment_my_page_order_detail_list_item_tvTitle = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvTitle)
        var fragment_my_page_order_detail_list_item_tvStoreName = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvStoreName)
        var fragment_my_page_order_detail_list_item_tvDate = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvDate)
        var fragment_my_page_order_detail_list_item_price = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_price)

        fun onBind(p: Post) {
            //fragment_my_page_order_detail_list_item_ivStore.setImageDrawable(p.storeImg)
            fragment_my_page_order_detail_list_item_tvTitle.text = p.title
            //fragment_my_page_order_detail_list_item_tvStoreName.text = p.storeName
            fragment_my_page_order_detail_list_item_tvDate.text = p.date.toString() // 수정해야함 지금은 귀찮
            fragment_my_page_order_detail_list_item_price.text = "${p.fund}원/${p.minPrice}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_order_detail, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.OrderDetailViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}