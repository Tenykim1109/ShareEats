package com.sharewanted.shareeats.src.main.mypage.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class NoticeAdapter(var list: MutableList<Notice>) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_notice_list_item_tvTitle = itemView.findViewById<TextView>(R.id.fragment_my_page_notice_list_item_tvTitle)
        var fragment_my_page_notice_list_item_tvDate = itemView.findViewById<TextView>(R.id.fragment_my_page_notice_list_item_tvDate)

        fun onBind(n: Notice) {
            fragment_my_page_notice_list_item_tvTitle.text = n.title
            fragment_my_page_notice_list_item_tvDate.text = n.Date

            itemView.setOnClickListener {
                itemClickListner.onClick(it, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_notice, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeAdapter.NoticeViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private lateinit var itemClickListner: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View,  position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}