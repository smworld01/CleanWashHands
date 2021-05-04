package com.hands.clean.ui.logs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entrys.DateCount
import com.hands.clean.ui.logs.adapter.RecyclerLogsAdapter

class LogsFragment : Fragment() {

    private lateinit var galleryViewModel: LogsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProvider(this).get(LogsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_logs, container, false)
        initLayout(root)
        return root
    }
    private fun initLayout(root: View) {
        initRecycler(root, arrayListOf(DateCount("test","0")))
    }
    private fun initRecycler(root: View, dateCount: List<DateCount>) {
        val recyclerViewLogs: RecyclerView = root.findViewById(R.id.recyclerViewLogs)
        val mAdapter = RecyclerLogsAdapter(dateCount)

        val lm = LinearLayoutManager(root.context)

        recyclerViewLogs.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(root.context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }
    }
}