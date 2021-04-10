package com.hands.clean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_tutorial.*
import kotlinx.android.synthetic.main.activity_tutorial1.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        initActionBar()

        buttonNext.setOnClickListener {
            val intent = Intent(this, TutorialActivity1::class.java)
            startActivity(intent)
        }



        buttonSkip.setOnClickListener {
             finish()
        }
    }
    private fun initActionBar() {
        supportActionBar?.title = "튜토리얼"
    }
}