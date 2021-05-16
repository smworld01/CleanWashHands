package com.hands.clean.function.room.entry

import androidx.recyclerview.widget.DiffUtil
import com.hands.clean.function.notification.type.NotifyInfo

interface LocationEntry {
    val uid: Int
    val name: String
    val isNotification: Boolean
    val notifyInfo: NotifyInfo

    companion object {
        object DateCountDiffCallback : DiffUtil.ItemCallback<LocationEntry>() {
            override fun areItemsTheSame(oldItem: LocationEntry, newItem: LocationEntry): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: LocationEntry, newItem: LocationEntry): Boolean {
                fun areContentsNameTheSame(oldItem: LocationEntry, newItem: LocationEntry): Boolean {
                    return oldItem.name == newItem.name
                }

                fun areContentsNotificationTheSame(oldItem: LocationEntry, newItem: LocationEntry): Boolean {
                    return oldItem.isNotification == newItem.isNotification
                }
                return areContentsNameTheSame(oldItem, newItem)
                        && areContentsNotificationTheSame(oldItem, newItem)
            }

        }
    }
}