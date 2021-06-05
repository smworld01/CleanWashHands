package com.hands.clean.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
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
        val itemList: List<TutorialItem> = arrayListOf(
            TutorialItem(null,"안녕하세요!\n이 앱에 대해 간단한 소개를 해드릴게요."),
            TutorialItem(ResourcesCompat.getDrawable(resources, R.drawable.ic_app_icon, null),"이 앱은 등록한 위치에 오면\n손을 씻으라고 알려주는 앱이에요."),
            TutorialItem(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_lock_24, null),"이 앱은 위치 정보를 사용하지만,\n인터넷을 사용하지 않아서 안전해요."),
        )
        pager.adapter = PagerRecyclerAdapter(itemList as ArrayList<TutorialItem>)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        dotsIndicator.setViewPager2(pager)

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position+1 == itemList.size) {
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
            if(pager.currentItem==itemList.size-1){
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
        supportActionBar?.title = "간단한 어플 소개"
    }

    class PagerRecyclerAdapter(private val pageList: ArrayList<TutorialItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_tutorial, parent, false))

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as PagerViewHolder).bind(getItem(position))
        }

        private fun getItem(position: Int): TutorialItem {
            return pageList[position]
        }

        override fun getItemCount(): Int = pageList.size


        class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.textView1)
            private val icon: AppCompatImageView = itemView.findViewById(R.id.icon)

            fun bind(item: TutorialItem) {
                icon.setImageDrawable(item.icon)
                textView.text = item.string
            }
        }
    }

    data class TutorialItem (
        val icon: Drawable?,
        val string: String
    )
}