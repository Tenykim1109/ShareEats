package com.sharewanted.shareeats.src.main.home.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class OrderInfoAdapter(var list: MutableList<MenuDetail>) : RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    inner class OrderInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMenu = itemView.findViewById<TextView>(R.id.fragment_order_info_tv_name)
        var tvDetail = itemView.findViewById<TextView>(R.id.fragment_order_info_tv_detail)
        var tvPrice = itemView.findViewById<TextView>(R.id.fragment_order_info_tv_price)
        var ivMenu = itemView.findViewById<ImageView>(R.id.fragment_order_info_iv_menu)

        fun onBind(detail: MenuDetail) {
            tvMenu.text = detail.name
            if (detail.detail != null) {
                tvDetail.text = detail.detail
                tvDetail.visibility = View.VISIBLE
            } else {
                tvDetail.visibility = View.INVISIBLE
            }
            tvPrice.text = detail.price.toString()
            ivMenu.setImageDrawable(detail.menuImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInfoAdapter.OrderInfoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_order_info_list_item_menu_detail, parent, false)
        return OrderInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderInfoAdapter.OrderInfoViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}