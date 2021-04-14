package com.hands.clean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tutorial)
        initActionBar()

        var pager:ViewPager2 = findViewById(R.id.pager1)
        val stringList: List<String> = arrayListOf("안녕하세요", "튜토리얼입니다.", "끝입니다.")
        pager.adapter = PagerRecyclerAdapter(stringList)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        buttonSkip.setOnClickListener {
             finish()
        }
    }
    private fun initActionBar() {
        supportActionBar?.title = "튜토리얼"
    }

}