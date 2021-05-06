package com.hands.clean

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PagerRecyclerAdapter(private val pageList: ArrayList<String>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_tutorial, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(pageList[position], position)
    }

    override fun getItemCount(): Int = pageList.size
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.textView1)

    fun bind(string: String, position: Int) {
        textView.text = string
    }
}