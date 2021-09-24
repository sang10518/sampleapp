package com.swc.common.util

import com.swc.common.util.DateTimeUtil.serviceDateFormat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
Created by sangwn.choi on2020-07-09

 **/
object DateTimeUtil {
    val nowUtcDate: Date get() = utcCalendar.time

    private val utcCalendar: Calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("UTC")
        timeInMillis = System.currentTimeMillis()
    }

    val apiDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    val serviceDateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm")

    const val serverDatePattern = "yyyy-MM-dd HH:mm:ss.S"

    fun getUtcDateFromMillis(time: Long):Date {
        return Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            timeInMillis = time
        }.time
    }

}

fun Date.toServiceString(): String? {
    return serviceDateFormat.format(this)
}