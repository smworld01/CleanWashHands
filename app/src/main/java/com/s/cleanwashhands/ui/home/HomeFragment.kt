package com.s.cleanwashhands.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s.cleanwashhands.R
import com.s.cleanwashhands.adapter.WashAdapter
import com.s.cleanwashhands.adapter.WashRecord
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var washRecord = arrayListOf<WashRecord>(
            WashRecord("2010-01-03", 3),
            WashRecord("2010-01-02", 2),
            WashRecord("2010-01-01", 1),
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initRecycler(root)

        return root
    }

    private fun initRecycler(root: View) {
        var recyclerViewWash: RecyclerView = root.findViewById(R.id.recyclerViewWash)
        var mAdapter = WashAdapter(washRecord)
        recyclerViewWash.adapter = mAdapter

        val lm = LinearLayoutManager(root.context)
        recyclerViewWash.layoutManager = lm
        recyclerViewWash.setHasFixedSize(true)
    }
}