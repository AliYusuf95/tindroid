package com.example.testapp.common.utils

import android.content.Context
import com.example.testapp.core.internal.InternalTinUiApi
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

/**
 * An interface that allows to format date-time objects as strings.
 */
public interface DateFormatter {

    /**
     * Formats the given date as a String.
     *
     * @param localDateTime The [LocalDateTime] to format as a String.
     * @return The formatted date-time string.
     */
    public fun formatDate(localDateTime: LocalDateTime?): String

    /**
     * Formats the given time as a String.
     *
     * @param localTime The [LocalTime] object to format as a String.
     * @return The formatted time string.
     */
    public fun formatTime(localTime: LocalTime?): String

    public companion object {
        /**
         * Builds the default date formatter.
         *
         * @param context The context of the application.
         * @return The default implementation of [DateFormatter].
         */
        @JvmStatic
        public fun from(context: Context): DateFormatter = DefaultDateFormatter(context)
    }
}

/**
 * Extension to be able to format objects of the deprecated [Date] type.
 */
@InternalTinUiApi
public fun DateFormatter.formatDate(date: Date?): String {
    return formatDate(date?.let(DateConverter::toLocalDateTime))
}

/**
 * Extension to be able to format objects of the deprecated [Date] type.
 */
@InternalTinUiApi
public fun DateFormatter.formatTime(date: Date?): String {
    return formatTime(date?.let(DateConverter::toLocalTime))
}
