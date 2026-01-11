package com.balanmaharaja.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private val dateFormat =
        SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())

    fun format(unixTimestamp: Long): String {
        return dateFormat.format(Date(unixTimestamp * 1000))
    }

    private val dbFormat =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun todayForDb(): String =
        dbFormat.format(Date())
}
