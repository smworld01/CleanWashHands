package com.hands.clean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        initActionBar()

         buttonSkip.setOnClickListener {
             finish()
        }
    }
    private fun initActionBar() {
        supportActionBar?.title = "튜토리얼"
    }
}