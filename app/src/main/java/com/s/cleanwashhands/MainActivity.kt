package com.s.cleanwashhands

import kotlinx.android.synthetic.main.activity_main.*

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.cleanwashhands.adapter.WashAdapter
import com.s.cleanwashhands.adapter.WashRecord

class MainActivity : AppCompatActivity() {
    var washRecord = arrayListOf<WashRecord>(
            WashRecord("2010-01-03", 3),
            WashRecord("2010-01-02", 2),
            WashRecord("2010-01-01", 1),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionBar()
        initLayout()

        startActivity(Intent(this, TutorialActivity::class.java))
    }
    
    private fun initActionBar() {
        supportActionBar?.title = "홈"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initLayout() {
        initRecycler()
        buttonSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    private fun initRecycler() {
    var mAdapter = WashAdapter(washRecord)
        recyclerViewWash.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        recyclerViewWash.layoutManager = lm
        recyclerViewWash.setHasFixedSize(true)
    }
}