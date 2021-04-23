package com.hands.clean.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.AppDatabase
import com.hands.clean.function.room.DateCount
import com.hands.clean.function.room.db
import com.hands.clean.function.room.useDatabase
import com.hands.clean.ui.home.adapter.RecyclerWashAdapter
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.text.observe(viewLifecycleOwner, Observer<String> {
            Log.e("test", it)
        })
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initLayout(root)


        return root
    }

    private fun initLayout(root: View) {
        thread {
            val dateCount: List<DateCount> = getCountByDate()
            initRecycler(root, dateCount)
            val todayCount = getTodayNotification()
            initTextView(root, todayCount)
        }

    }

    private fun initRecycler(root: View, dateCount: List<DateCount>) {
        val recyclerViewWash: RecyclerView = root.findViewById(R.id.recyclerViewWash)
        val mAdapter = RecyclerWashAdapter(dateCount)

        val lm = LinearLayoutManager(root.context)

        recyclerViewWash.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(root.context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }
    }

    private fun getCountByDate(): List<DateCount> {
        return db.washDao().countByDate()
    }

    private fun initTextView(root: View, todayCount: Int) {
        val textView: TextView = root.findViewById(R.id.textView)

        textView.text = "오늘은 ${todayCount}번 알림을 받으셨어요"

    }

    private fun getTodayNotification(): Int {
        return db.washDao().countToday()
    }
}