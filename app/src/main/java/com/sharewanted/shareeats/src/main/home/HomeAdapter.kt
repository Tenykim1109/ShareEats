package com.sharewanted.shareeats.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sharewanted.shareeats.R

class HomeAdapter(var list: MutableList<HomeMenu>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivStore = itemView.findViewById<ImageView>(R.id.fragment_main_list_item_ivStore)
        var tvTitle = itemView.findViewById<TextView>(R.id.fragment_main_list_item_tvTitle)
        var tvStore = itemView.findViewById<TextView>(R.id.fragment_main_list_item_tvStoreName)
        var tvPrice = itemView.findViewById<TextView>(R.id.fragment_main_list_item_price)

        fun onBind(menu: HomeMenu) {
            ivStore.setImageDrawable(menu.image)
            tvTitle.text = menu.title
            tvStore.text = menu.storeName
            tvPrice.text = "${menu.currentPrice}원/${menu.minPrice}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_list_item_menu, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}