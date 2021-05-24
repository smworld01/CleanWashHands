package com.hands.clean.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.hands.clean.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tutorial)
        initActionBar()

        val buttonNext:Button = findViewById(R.id.buttonNext)
        val buttonSkip:Button = findViewById(R.id.buttonSkip)

        val pager:ViewPager2 = findViewById(R.id.pager1)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        val stringList: List<String> = arrayListOf("안녕하세요\n" + "튜토리얼 입니다.", "튜토리얼입니다.", "끝입니다.")
        pager.adapter = PagerRecyclerAdapter(stringList as ArrayList<String>)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        dotsIndicator.setViewPager2(pager)

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position+1 == stringList.size) {
                    buttonNext.text = "완료"
                    buttonSkip.visibility = View.GONE
                }
                else {
                    buttonNext.text = "다음"
                    buttonSkip.visibility = View.VISIBLE
                }
            }
        })

        buttonNext.setOnClickListener {
            if(pager.currentItem==stringList.size-1){
                finish()
            }
            else
                pager.currentItem = pager.currentItem+1
        }

        buttonSkip.setOnClickListener {
            finish()
        }
    }
    private fun initActionBar() {
        supportActionBar?.title = "튜토리얼"
    }

}