package com.hands.clean.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R

class RecyclerDeviceAdapter (private val RecyclerDeviceData :ArrayList<RecyclerDeviceData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class RecyclerDeviceItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var switchDevice: SwitchCompat = itemView.findViewById(R.id.switchDevice)

        fun bind (data: RecyclerDeviceData) {
            switchDevice.text = data.name
            switchDevice.setOnCheckedChangeListener { compoundButton, isChecked ->
                // Todo update SQLite table
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.device_recycler_item, parent, false)
        return RecyclerDeviceItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerDeviceItem: RecyclerDeviceItem = holder as RecyclerDeviceItem
        recyclerDeviceItem.bind(RecyclerDeviceData[position])
    }

    override fun getItemCount(): Int = RecyclerDeviceData.size
}

fun adaptRecyclerDevice(context: Context, recyclerView: RecyclerView, arrayList :ArrayList<RecyclerDeviceData>): RecyclerView {
    val mAdapter = RecyclerDeviceAdapter(arrayList)
    val lm = LinearLayoutManager(context)

    recyclerView.apply {
        setHasFixedSize(true)
        adapter = mAdapter
        layoutManager = lm
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
        addItemDecoration(itemDecoration)
    }
    return recyclerView
}