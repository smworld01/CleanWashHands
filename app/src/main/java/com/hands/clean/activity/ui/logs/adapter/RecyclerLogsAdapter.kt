package com.hands.clean.activity.ui.logs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entry.WashEntry

class RecyclerLogsAdapter(private val washData: List<WashEntry>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById<TextView>(R.id.logsDate)
        var detail: TextView = itemView.findViewById<TextView>(R.id.logsDetail)

        fun bind (data: WashEntry) {
            date.text = data.date
            detail.text = data.detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        val holder:RecyclerView.ViewHolder
        view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_logs, parent, false)
        holder = RecyclerWashItem(view)

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
        recyclerWashItem.bind(washData[position])
    }

    override fun getItemCount(): Int {
        return washData.size
    }
}