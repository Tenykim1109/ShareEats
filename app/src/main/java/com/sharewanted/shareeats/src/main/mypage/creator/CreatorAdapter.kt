package com.sharewanted.shareeats.src.main.mypage.creator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class CreatorAdapter(var list: MutableList<Creator>) : RecyclerView.Adapter<CreatorAdapter.CreatorViewHolder>() {

    inner class CreatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fragment_my_page_creator_list_item_tvName = itemView.findViewById<TextView>(R.id.fragment_my_page_creator_list_item_tvName)
        var fragment_my_page_creator_list_item_tvEmail = itemView.findViewById<TextView>(R.id.fragment_my_page_creator_list_item_tvEmail)

        fun onBind(c: Creator) {
            fragment_my_page_creator_list_item_tvName.text = c.name
            fragment_my_page_creator_list_item_tvEmail.text = c.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_page_list_item_creator, parent, false)
        return CreatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatorAdapter.CreatorViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}