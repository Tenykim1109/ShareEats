package com.sharewanted.shareeats.src.main.home.order.selectMenu

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu

class SelectMenuAdapter(var menuList: MutableList<StoreMenu>): RecyclerView.Adapter<SelectMenuAdapter.SelectMenuHolder>() {

    var quantityArr = Array(menuList.size) { 0 }

    inner class SelectMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo = itemView.findViewById<ImageView>(R.id.activity_select_menu_iv_photo)
        val menuName = itemView.findViewById<TextView>(R.id.activity_select_menu_tv_name)
        val desc = itemView.findViewById<TextView>(R.id.activity_select_menu_tv_desc)
        val price = itemView.findViewById<TextView>(R.id.activity_select_menu_tv_price)
        val quantity = itemView.findViewById<TextView>(R.id.activity_select_menu_tv_quantity)
        val plusBtn = itemView.findViewById<ImageView>(R.id.activity_select_menu_iv_plus)
        val minusBtn = itemView.findViewById<ImageView>(R.id.activity_select_menu_iv_minus)

        fun bindinfo(data: StoreMenu, index: Int) {

            Glide.with(itemView.context).load(data.photo).into(photo)
            menuName.text = data.name
            desc.text = data.desc
            price.text = data.price.toString()

            plusBtn.setOnClickListener {
                var count = quantity.text.toString().toInt()
                count++
                quantity.text = count.toString()
                quantityArr[index] = count
            }

            minusBtn.setOnClickListener {
                var count = quantity.text.toString().toInt()
                if (quantity.text.toString().toInt() > 0) {
                    count--
                    quantity.text = count.toString()
                    quantityArr[index] = count
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectMenuAdapter.SelectMenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_select_menu_list_item, parent, false)
        return SelectMenuHolder(view)
    }

    override fun onBindViewHolder(holder: SelectMenuAdapter.SelectMenuHolder, position: Int) {
        holder.bindinfo(menuList[position], position)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }


}