package com.hands.clean.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.ui.home.adapter.RecyclerDeviceAdapter
import com.hands.clean.ui.home.adapter.RecyclerDeviceData

class WifiSettingActivity : AppCompatActivity() {
    private var deviceList= arrayListOf<RecyclerDeviceData>(
            RecyclerDeviceData("KT"), RecyclerDeviceData("SKT")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_setting)

        initActionBar()
        initRecycler()
    }

    private fun initActionBar() {
        supportActionBar?.title = "와이파이 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
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
    private fun initRecycler() {
        val recyclerViewWash: RecyclerView = findViewById(R.id.recyclerViewWifi)
        val mAdapter = RecyclerDeviceAdapter(deviceList)
        val context = this
        val lm = LinearLayoutManager(this)

        recyclerViewWash.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }

    }
}