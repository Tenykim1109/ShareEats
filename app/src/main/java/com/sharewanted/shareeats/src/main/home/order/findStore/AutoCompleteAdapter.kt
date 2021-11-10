package com.sharewanted.shareeats.src.main.home.order.findStore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.src.main.home.order.OrderActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.Store

class AutoCompleteAdapter(context: Context, private val layoutResource: Int, private val storeList: MutableList<Store>) : ArrayAdapter<Store>(context, layoutResource, storeList),
    Filterable {

        private var mList: MutableList<Store> = storeList

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Store? {
        return mList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_find_store_list_item, parent, false)
        val profile = view.findViewById<ImageView>(R.id.activity_find_store_iv_profile)
        val name = view.findViewById<TextView>(R.id.activity_find_store_tv_name)
        val tel = view.findViewById<TextView>(R.id.activity_find_store_tv_tel)
        val location = view.findViewById<TextView>(R.id.activity_find_store_tv_location)
        val time = view.findViewById<TextView>(R.id.activity_find_store_tv_time)

        Glide.with(context).load(storeList[position].profile).into(profile)
        name.text = storeList[position].name
        tel.text = storeList[position].tel
        location.text = storeList[position].location
        time.text = storeList[position].time

        view.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java).apply {
                putExtra("resultType", "findStore")
                putExtra("storeId", storeList[position].storeId)
                putExtra("storeName", storeList[position].name)
                putExtra("storeMinPrice", storeList[position].minPrice.toString())
            }
            (context as Activity).setResult(Activity.RESULT_OK, intent)
            (context as Activity).finish()
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0?.toString()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    storeList
                else
                    storeList.filter {
                        it.name.contains(queryString) || it.location.contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mList = p1!!.values as MutableList<Store>
                notifyDataSetChanged()
            }

        }
    }

}