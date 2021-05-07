package com.hands.clean.function

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.PI
import kotlin.math.abs


fun compareStringTimeByAbsoluteMinute(stringTime1: String, stringTime2: String): Long {
    val msTime1 = convertStringToMsTime(stringTime1)
    val msTime2 = convertStringToMsTime(stringTime2)

    return abs(msTime1 - msTime2) / (100*60)
}


fun convertStringToMsTime(stringTime: String): Long {
    val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
    val date = mFormat.parse(stringTime)
    return if (date is Date) {
        date.time
    } else {
        0
    }
}