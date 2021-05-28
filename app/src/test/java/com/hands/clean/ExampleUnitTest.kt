package com.hands.clean

import android.util.Log
import com.hands.clean.function.compareStringTimeByAbsoluteMinute
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.TrackerEntry
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)


        compare_list()
    }

    fun compare_list() {
        val gpsEntryList = mutableListOf(
            GpsEntry(uid=7, name="home", requestId="246665989", latitude=37.421867983010415, longitude=-122.0840310305357, radius=23.0f),
            GpsEntry(uid=10, name="test", requestId="1928531225", latitude=37.42126088377453, longitude=-122.08418693393469, radius=23.0f),
        )
        val newList = mutableListOf(
            GpsEntry(uid=10, name="test", requestId="1928531225", latitude=37.42126088377453, longitude=-122.08418693393469, radius=23.0f),
        )
        print("newList $newList\noldList $gpsEntryList\n")
        val diffCallback = TrackerEntry.Companion.DateCountDiffCallback
        val newItems = newList.toList().iterator()
        val oldItems = gpsEntryList.toList().iterator()

        if (!newItems.hasNext()) {
            oldItems.forEach {
                gpsEntryList.remove(it)
            }
            return
        } else if (!oldItems.hasNext()) {
            newItems.forEach {
                gpsEntryList.add(it)
            }
            return
        }

        var newItem: GpsEntry? = null
        var oldItem: GpsEntry? = null
        while (newItems.hasNext() || oldItems.hasNext()) {
            if (newItem == null || oldItem == null) {
                newItem = newItems.next()
                oldItem = oldItems.next()
            } else if (oldItem.uid < newItem.uid){
                if (oldItems.hasNext()) oldItem = oldItems.next()
            } else if (oldItem.uid > newItem.uid) {
                if (newItems.hasNext()) newItem = newItems.next()
            } else {
                newItem = newItems.next()
                oldItem = oldItems.next()
            }

            if (diffCallback.areItemsTheSame(oldItem, newItem)) {
                if (!diffCallback.areContentsTheSame(oldItem, newItem)) {
                    modifyGpsEntry(oldItem, newItem)
                }
            } else {
                if (oldItem.uid < newItem.uid) {
                    gpsEntryList.remove(oldItem)
                } else {
                    gpsEntryList.add(newItem)
                }
            }
        }
        while (newItems.hasNext()) {
            newItem = newItems.next()
            gpsEntryList.add(newItem)
        }
        while (oldItems.hasNext()) {
            oldItem = oldItems.next()
            gpsEntryList.remove(oldItem)
        }
        print("newList $newList\noldList $gpsEntryList\n")
    }

    fun modifyGpsEntry(oldGpsEntry: GpsEntry, newGpsEntry: GpsEntry) {
        Log.e("modify", "$oldGpsEntry || $newGpsEntry")
        oldGpsEntry.radius = newGpsEntry.radius
        oldGpsEntry.name = newGpsEntry.name
        oldGpsEntry.marker?.let {
            it.title = newGpsEntry.name
        }
        oldGpsEntry.circle?.let {
            it.radius = newGpsEntry.radius.toDouble()
        }
    }
}