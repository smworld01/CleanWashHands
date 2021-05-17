package com.hands.clean.activity.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.adapter.TrackerEntryListAdapter
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.TrackerEntry

class TrackerListActivity : AppCompatActivity() {
    private lateinit var type: String


    private lateinit var textViewEmptyRecycler: TextView

    private lateinit var mAdapter: TrackerEntryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker_list)

        type = intent.getStringExtra("type")!!

        initActionBar()
        initRecyclerView()

    }

    private fun initActionBar() {
        supportActionBar?.title = "관리"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        val recyclerViewTracker: RecyclerView = findViewById(R.id.recyclerViewTracker)
        textViewEmptyRecycler = findViewById(R.id.textViewEmptyRecycler)

        mAdapter = adaptRecyclerDevice(recyclerViewTracker)

        when (type) {
            NotifyType.Bluetooth.channelId -> {
                textViewEmptyRecycler.text = "등록된 블루투스 기기가 없습니다."
                DB.getInstance().bluetoothDao().getAllByLiveData().observe(this) { observe(it) }
            }
            NotifyType.GPS.channelId -> {
                textViewEmptyRecycler.text = "등록된 위치가 없습니다."
                DB.getInstance().gpsDao().getAllByLiveData().observe(this) { observe(it) }
            }
            NotifyType.Wifi.channelId -> {
                textViewEmptyRecycler.text = "등록된 와이파이 기기가 없습니다."
                DB.getInstance().wifiDao().getAllByLiveData().observe(this) { observe(it) }
            }
        }
    }

    private fun observe(entries :List<TrackerEntry>) {
        mAdapter.submitList(entries)
        if (entries.isEmpty()) {
            textViewEmptyRecycler.visibility = View.VISIBLE
        } else {
            textViewEmptyRecycler.visibility = View.GONE
        }
    }


    private fun adaptRecyclerDevice(recyclerView: RecyclerView): TrackerEntryListAdapter {
        val mAdapter = TrackerEntryListAdapter()
        val lm = LinearLayoutManager(this)

        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }
        return mAdapter
    }
}