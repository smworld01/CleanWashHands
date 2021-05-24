package com.hands.clean.activity.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entry.DateCount

class WashListAdapterWithHeader : ListAdapterWithHeader<DateCount, RecyclerView.ViewHolder>(DateCount.Companion.DateCountDiffCallback) {
    private val typeHeader = 0
    private val typeItem = 1


    inner class RecyclerWashHeader(headerView: View): RecyclerView.ViewHolder(headerView)
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var count: TextView = itemView.findViewById(R.id.count)

        fun bind (data: DateCount) {
            date.text = data.date
            count.text = data.count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view : View?
        val holder:RecyclerView.ViewHolder
        if (viewType == typeHeader) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_wash_header, parent, false)
            holder = RecyclerWashHeader(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_wash, parent, false)
            holder = RecyclerWashItem(view)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerWashHeader) {
//            var recyclerWashHeader: RecyclerWashHeader = holder as RecyclerWashHeader
        } else {
            val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
            recyclerWashItem.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) typeHeader
        else typeItem
    }
}