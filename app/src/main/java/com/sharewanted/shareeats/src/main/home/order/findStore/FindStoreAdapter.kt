package com.sharewanted.shareeats.src.main.home.order.findStore

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Store

class FindStoreAdapter(val storeList: MutableList<Store>) : RecyclerView.Adapter<FindStoreAdapter.FindStoreHolder>() {

    inner class FindStoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val profile = itemView.findViewById<ImageView>(R.id.activity_find_store_iv_profile)
        val name = itemView.findViewById<TextView>(R.id.activity_find_store_tv_name)
        val tel = itemView.findViewById<TextView>(R.id.activity_find_store_tv_tel)
        val location = itemView.findViewById<TextView>(R.id.activity_find_store_tv_location)
        val time = itemView.findViewById<TextView>(R.id.activity_find_store_tv_time)

        fun bindInfo(data: Store) {

            Glide.with(itemView.context).load(data.profile).into(profile)
            name.text = data.name
            tel.text = data.tel
            location.text = data.location
            time.text = data.time

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, OrderActivity::class.java).apply {
                    putExtra("resultType", "findStore")
                    putExtra("storeId", data.storeId)
                    putExtra("storeName", data.name)
                    putExtra("storeMinPrice", data.minPrice.toString())
                }
                (itemView.context as Activity).setResult(Activity.RESULT_OK, intent)
                (itemView.context as Activity).finish()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindStoreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_find_store_list_item, parent, false)
        return FindStoreHolder(view)
    }

    override fun onBindViewHolder(holder: FindStoreHolder, position: Int) {
        holder.bindInfo(storeList[position])
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}