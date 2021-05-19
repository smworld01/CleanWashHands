package com.hands.clean.function.room.entry

import androidx.recyclerview.widget.DiffUtil
import com.hands.clean.function.notification.type.NotifyInfo

interface TrackerEntry {
    val uid: Int
    var name: String
    var isNotification: Boolean
    val notifyInfo: NotifyInfo
    var lastNotifyTime: String?
    val registrationTime: String

    companion object {
        object DateCountDiffCallback : DiffUtil.ItemCallback<TrackerEntry>() {
            override fun areItemsTheSame(oldItem: TrackerEntry, newItem: TrackerEntry): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: TrackerEntry, newItem: TrackerEntry): Boolean {
                fun areContentsNameTheSame(oldItem: TrackerEntry, newItem: TrackerEntry): Boolean {
                    return oldItem.name == newItem.name
                }

                fun areContentsNotificationTheSame(oldItem: TrackerEntry, newItem: TrackerEntry): Boolean {
                    return oldItem.isNotification == newItem.isNotification
                }

                val result = areContentsNameTheSame(oldItem, newItem)
                        && areContentsNotificationTheSame(oldItem, newItem)

                if (oldItem is GpsEntry && newItem is GpsEntry) {
                    return result.and(oldItem.radius == newItem.radius)
                }

                return result
            }
        }
    }
}