package com.hands.clean.activity.settings.wifi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.dialog.DeviceRegisterDialog
import com.hands.clean.function.adapter.WifiDetectedListAdapter
import com.hands.clean.function.adapter.WifiScanListAdapter
import com.hands.clean.function.room.DB

class WifiDetectedActivity : AppCompatActivity() {
    private lateinit var viewModel: WifiDetectedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_detected)

        initActionBar()

        viewModel = ViewModelProvider(this).get(WifiDetectedViewModel::class.java)

        initRecyclerView()
    }

    private fun initActionBar() {
        supportActionBar?.title = "와이파이 등록"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    // 상단바 뒤로가기 버튼 이벤트
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
        val recyclerViewWifiDetected: RecyclerView = findViewById(R.id.recyclerViewWifiDetected)
        val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)
        val mAdapter = adaptRecycler(recyclerViewWifiDetected)

        DB.getInstance().wifiDao().getAllByLiveData().observe(this) { entries ->
            val filteredEntries = entries.filter {
                it.name.isEmpty()
            }
            if (filteredEntries.isEmpty()) {
                textViewEmptyRecycler.visibility = View.VISIBLE
            } else {
                textViewEmptyRecycler.visibility = View.GONE
                mAdapter.submitList(filteredEntries)
            }
        }

        viewModel.createWifiEntry.observe(this) {
            if (it != null) {
                val dialog = DeviceRegisterDialog(it)
                dialog.show(this.supportFragmentManager, dialog.tag)
            }
        }
    }
    private fun adaptRecycler(recyclerView: RecyclerView): WifiDetectedListAdapter {
        val mAdapter = WifiDetectedListAdapter(viewModel)
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