package com.hands.clean.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.hands.clean.PagerRecyclerAdapter
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
        val stringList: List<String> = arrayListOf("안녕하세요\n" + "손씻기 어플에 대한 튜토리얼 입니다.", "우선 와이파이에 대한 설정입니다.\n" +
                "와이파이에 관한 알림설정 및 \n" + "기기관리가 가능합니다.", "이 다음은 블루투스에 관한 설명입니다.\n" + "블루투스에 대한 알림설정과 등록, 기기관리가 \n" + "가능합니다.","GPS에 관한 설명입니다.\n"
                + "GPS에 대한 알림설정과 등록된 위치 관리\n" + "위치찾기가 가능합니다.\n" + "저희 어플이 다른 위치권한과 다른점은 \n"
                + "위치권한을 사용하지만 인터넷을 사용하지 않아 \n " + "안전합니다.", "이상으로 튜토리얼을 마치겠습니다.\n" + "감사합니다.")
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