package com.hands.clean.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.ui.home.adapter.RecyclerWashAdapter
import com.hands.clean.ui.home.adapter.RecyclerWashData

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var washRecord = arrayListOf<RecyclerWashData>(
            RecyclerWashData("2010-01-03", 3),
            RecyclerWashData("2010-01-02", 2),
            RecyclerWashData("2010-01-01", 1),
    )

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

        initRecycler(root)

        return root
    }

    private fun initRecycler(root: View) {
        val recyclerViewWash: RecyclerView = root.findViewById(R.id.recyclerViewWash)
        val mAdapter = RecyclerWashAdapter(washRecord)

        val lm = LinearLayoutManager(root.context)

        recyclerViewWash.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(root.context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.divider, null)!!)
            addItemDecoration(itemDecoration)
        }

    }
}