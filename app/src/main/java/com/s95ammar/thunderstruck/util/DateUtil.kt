package com.s95ammar.thunderstruck.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val PATTERN_DAY_OF_WEEK_ABBR = "EEE"
    const val PATTERN_DATE_WITH_MONTH_ABBR = "dd MMM"
    const val PATTERN_FULL_DATE = "dd MMM yyyy hh:mm:ss aa"

    fun getDateShortFormat(date: Date): String {
        return SimpleDateFormat(PATTERN_DATE_WITH_MONTH_ABBR, Locale.US).format(date)
    }

    fun getDayOfWeekAbbr(date: Date): String {
        return SimpleDateFormat(PATTERN_DAY_OF_WEEK_ABBR, Locale.US).format(date)
    }

    fun getFullDateFormatted(date: Date): String {
        return SimpleDateFormat(PATTERN_FULL_DATE, Locale.US).format(date)
    }
}