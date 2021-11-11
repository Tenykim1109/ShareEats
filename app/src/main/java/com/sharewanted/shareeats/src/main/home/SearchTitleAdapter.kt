package com.sharewanted.shareeats.src.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post

class SearchTitleAdapter(context: Context, private val layoutResource: Int, private val postList: MutableList<Post>) : ArrayAdapter<Post>(context, layoutResource, postList),
    Filterable {

    private var mList: MutableList<Post> = postList

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Post? {
        return mList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
        var tvTitle = view.findViewById<TextView>(R.id.fragment_home_tv_title)
        var tvPlace = view.findViewById<TextView>(R.id.fragment_home_tv_delivery_place)
        var tvFund = view.findViewById<TextView>(R.id.fragment_home_tv_fund)
        var progress = view.findViewById<LinearProgressIndicator>(R.id.fragment_home_list_item_progress_bar)

        tvTitle.text = postList[position].title
        tvPlace.text = postList[position].place
        tvFund.text = "${CommonUtils().makeComma(postList[position].fund)} / ${CommonUtils().makeComma(postList[position].minPrice)}"

        progress.max = postList[position].minPrice
        progress.progress = postList[position].fund

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0?.toString()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    postList
                else
                    postList.filter {
                        it.title.contains(queryString) || it.content.contains(queryString) || it.place.contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mList = p1!!.values as MutableList<Post>
                notifyDataSetChanged()
            }

        }
    }


    private fun refreshPost() {
        notifyDataSetChanged()
    }

}