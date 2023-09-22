package com.reader.bd_bank_info.common.utils

import java.text.SimpleDateFormat
import java.util.*

const val TZ_UTC = "UTC"
const val ISO_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
const val COMMON_DATE_FORMAT = "dd MMMM yyyy hh:mm:ss a"

private val utcTimeZone = TimeZone.getTimeZone(TZ_UTC)

private val dateParser =
    SimpleDateFormat(ISO_UTC_FORMAT, Locale.ENGLISH).apply { timeZone = utcTimeZone }
private var dateFormatter = SimpleDateFormat(COMMON_DATE_FORMAT, Locale.getDefault()).apply {
    timeZone = TimeZone.getDefault()
}

fun setDateFormatterLocale(locale: Locale) {
    dateFormatter = SimpleDateFormat(COMMON_DATE_FORMAT, locale)
}

fun setDateFormatterTimeZone(timeZone: TimeZone) {
    dateFormatter.timeZone = timeZone
}

fun parseDate(dateString: String?, pattern: String = ISO_UTC_FORMAT): Date? {
    dateString ?: return null

    return try {
        if (dateParser.toPattern() != pattern) {
            dateParser.applyPattern(pattern)
        }
        dateParser.parse(dateString)
    } catch (ex: Exception) {
        return null
    }
}

fun formatDate(date: Date?, pattern: String = COMMON_DATE_FORMAT): String? {
    date ?: return null

    return try {
        if (dateFormatter.toPattern() != pattern) {
            dateFormatter.applyPattern(pattern)
        }
        dateFormatter.format(date)
    } catch (ex: Exception) {
        return null
    }
}

fun formatDateString(
    dateString: String?,
    actualPattern: String = ISO_UTC_FORMAT,
    newPattern: String = COMMON_DATE_FORMAT
): String? {
    return formatDate(parseDate(dateString, actualPattern), newPattern)
}