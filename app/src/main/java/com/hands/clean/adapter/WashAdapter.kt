package com.hands.clean.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R

class WashAdapter (private val washRecord :ArrayList<WashRecord>) : RecyclerView.Adapter<WashAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.date)
        val count = itemView.findViewById<TextView>(R.id.count)

        fun bind (item: WashRecord) {
            date.text = item.date
            count.text = item.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(washRecord[position])
    }

    override fun getItemCount(): Int = washRecord.size

}