package com.hands.clean.ui.logs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DateCount

class RecyclerLogsAdapter(private val washData: List<DateCount>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1


    inner class RecyclerWashHeader(headerView: View): RecyclerView.ViewHolder(headerView)
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById<TextView>(R.id.date)
        var count: TextView = itemView.findViewById<TextView>(R.id.count)

        fun bind (data: DateCount) {
            date.text = data.date
            count.text = data.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        val holder:RecyclerView.ViewHolder
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.wash_recycler_header, parent, false)
            holder = RecyclerWashHeader(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.wash_recycler_item, parent, false)
            holder = RecyclerWashItem(view)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerWashHeader) {
//            var recyclerWashHeader: RecyclerWashHeader = holder as RecyclerWashHeader
        } else {
            val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
            recyclerWashItem.bind(washData[position - 1])
        }
    }

    override fun getItemCount(): Int = washData.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) TYPE_HEADER
        else TYPE_ITEM
    }
}