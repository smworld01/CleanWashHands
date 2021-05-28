package com.hands.clean.function.adapter

import androidx.recyclerview.widget.*

abstract class ListAdapterWithHeader<T, VH : RecyclerView.ViewHolder>(
        private val diffCallback: DiffUtil.ItemCallback<T>,
        private val headerOffset: Int = 1
) : RecyclerView.Adapter<VH>() {

    private val mHelper by lazy {
        AsyncListDiffer<T>(
            OffsetListUpdateCallback(this, headerOffset),
            AsyncDifferConfig.Builder(diffCallback).build()
        )
    }

    fun submitList(list: List<T>?) {
        mHelper.submitList(list)
    }

    fun getItem(position: Int): T {
        return mHelper.currentList[position - headerOffset]
    }

    override fun getItemCount(): Int {
        return mHelper.currentList.size + headerOffset
    }

    private class OffsetListUpdateCallback(
        private val adapter: RecyclerView.Adapter<*>,
        private val offset: Int
    ) : ListUpdateCallback {

        private fun offsetPosition(originalPosition: Int): Int {
            return originalPosition + offset
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeInserted(offsetPosition(position), count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyItemRangeRemoved(offsetPosition(position), count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyItemMoved(offsetPosition(fromPosition), offsetPosition(toPosition))
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(offsetPosition(position), count, payload)
        }
    }
}