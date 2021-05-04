package com.hands.clean.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.DateCount
import com.hands.clean.ui.home.adapter.WashListAdapterWithHeader

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
        initRecycler(root)
        initTextView(root)

    }

    private fun initRecycler(root: View) {
        val recyclerViewWash: RecyclerView = root.findViewById(R.id.recyclerViewWash)

        val mAdapter = WashListAdapterWithHeader()

        val lm = LinearLayoutManager(root.context)

        recyclerViewWash.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(root.context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }

        getCountByDate().observe(this.viewLifecycleOwner, {
            mAdapter.submitList(it)
        })
    }

    private fun getCountByDate(): LiveData<MutableList<DateCount>> {
        return DB.getInstance().washDao().countByDate()
    }

    private fun initTextView(root: View) {
        val textView: TextView = root.findViewById(R.id.textView)

        textView.text = "오늘은 0번 알림을 받으셨어요"

        val todayCount = getTodayNotification()

        // todo UTC 관련 오류가 있음 ex) 서울은 +9인데 sql now()는 +0
        todayCount.observe(this.viewLifecycleOwner, {
            textView.text = "오늘은 ${it}번 알림을 받으셨어요"
        })
    }

    private fun getTodayNotification(): LiveData<Int> {
        return DB.getInstance().washDao().countToday()
    }
}