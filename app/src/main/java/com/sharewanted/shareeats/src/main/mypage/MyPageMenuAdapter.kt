package com.sharewanted.shareeats.src.main.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class MyPageMenuAdapter(var list: MutableList<String>, val listener: MyPageMenuClickListener) : RecyclerView.Adapter<MyPageMenuAdapter.MyPageMenuViewHolder>() {

    inner class MyPageMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_list_item_tvMenu = itemView.findViewById<TextView>(R.id.fragment_my_page_order_detail_list_item_tvTitle)

        fun onBind(s: String) {
            fragment_my_page_list_item_tvMenu.text = s

            itemView.setOnClickListener {
                listener.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageMenuViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_menu, parent, false)
        return MyPageMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPageMenuAdapter.MyPageMenuViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}