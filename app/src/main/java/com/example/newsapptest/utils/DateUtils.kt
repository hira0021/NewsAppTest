package com.example.newsapptest.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate1(publishedAt: String?): String {
        // EEE, dd MMM HH.mm == Sen, 25 April 20.34
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("EEE, dd MMM HH.mm", Locale.getDefault())

            val date = inputFormat.parse(publishedAt ?: "")
            date?.let { outputFormat.format(it) } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun formatDate2(publishedAt: String?): String {
        // "EEEE, dd MMM" == "Senin, 25 Apr"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())

            val date = inputFormat.parse(publishedAt ?: "")
            date?.let { outputFormat.format(it) } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

}
