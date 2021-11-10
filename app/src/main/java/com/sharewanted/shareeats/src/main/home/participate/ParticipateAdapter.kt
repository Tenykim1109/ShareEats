package com.sharewanted.shareeats.src.main.home.participate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu

class ParticipateAdapter(var list: MutableList<Menu>) : RecyclerView.Adapter<ParticipateAdapter.ParticipateViewHolder>() {

    inner class ParticipateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMenu = itemView.findViewById<TextView>(R.id.fragment_participate_tv_menu)
        var tvQty = itemView.findViewById<TextView>(R.id.fragment_participate_tv_qty)
        var tvPrice = itemView.findViewById<TextView>(R.id.fragment_participate_tv_price)

        fun onBind(m: Menu) {
            tvMenu.text = m.name
            tvQty.text = "${m.quantity}개"
            tvPrice.text = "${(m.price * m.quantity)}원"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipateViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_participate_list_item_menu, parent, false)
        return ParticipateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipateViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}