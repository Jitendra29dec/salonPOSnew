package com.hubwallet.apptspos.Utils

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import com.hubwallet.apptspos.view.week_view.EventModel
import com.hubwallet.apptspos.view.week_view.WeekViewDisplayable
import java.util.*

class EventHelper {
    fun getEventsInRange(
            startDate: Calendar,
            endDate: Calendar,
            color:Int
    ): List<WeekViewDisplayable<EventModel>> {
        val year = startDate.get(Calendar.YEAR)
        val month = startDate.get(Calendar.MONTH)

        val idOffset = year + 10L * month
        val events = mutableListOf<WeekViewDisplayable<EventModel>>()

        events += newEvent(
                id = idOffset + 1,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 16,
                minute = 0,
                duration = 90,
                color = color
        )

        // Add multi-day event
        events += newEvent(
                id = idOffset + 2,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 27,
                hour = 20,
                minute = 0,
                duration = 5 * 60,
                color = color
        )

        events += newEvent(
                id = idOffset + 3,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 9,
                minute = 30,
                duration = 60,
                color = color,
                isCanceled = true
        )

        events += newEvent(
                id = idOffset + 3,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 9,
                minute = 30,
                duration = 60,
                color = color
        )

        events += newEvent(
                id = idOffset + 4,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 10,
                minute = 30,
                duration = 45,
                color = color
        )

        events += newEvent(
                id = idOffset + 5,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 12,
                minute = 30,
                duration = 2 * 60,
                color = color
        )

        events += newEvent(
                id = idOffset + 6,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 17,
                hour = 11,
                minute = 0,
                duration = 4 * 60,
                color = color
        )

        events += newEvent(
                id = idOffset + 7,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 15,
                hour = 3,
                minute = 0,
                duration = 3 * 60,
                color = color,
                isCanceled = true
        )

        events += newEvent(
                id = idOffset + 8,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 1,
                hour = 9,
                minute = 0,
                duration = 3 * 60,
                color = color
        )

        events += newEvent(
                id = idOffset + 9,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = startDate.getActualMaximum(Calendar.DAY_OF_MONTH),
                hour = 15,
                minute = 0,
                duration = 3 * 60,
                color = color
        )

        // All-day event
        events += newEvent(
                id = idOffset + 10,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 0,
                minute = 0,
                duration = 24 * 60,
                isAllDay = true,
                color = color
        )

        // All-day event
        events += newEvent(
                id = idOffset + 11,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 28,
                hour = 0,
                minute = 0,
                duration = 24 * 60,
                isAllDay = true,
                color = color
        )

        // All-day event until 00:00 next day
        events += newEvent(
                id = idOffset + 12,
                eventTitle = "First Event",
                year = year,
                month = month,
                dayOfMonth = 14,
                hour = 0,
                minute = 0,
                duration = 10 * 60,
                isAllDay = true,
                color = color
        )

        return events
    }
    public fun getStartDate(): Calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
    }

    public fun getEndDate(): Calendar = Calendar.getInstance().apply {
        val daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        set(Calendar.DAY_OF_MONTH, daysInMonth)
        set(Calendar.HOUR_OF_DAY, 23)
    }


    public fun newEvent(
            id: Long,
            eventTitle:String,
            year: Int,
            month: Int,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            duration: Int,
            color: Int,
            isAllDay: Boolean = false,
            isCanceled: Boolean = false
    ): EventModel {
        val startTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.MINUTE, duration)


        val spannableTitle = SpannableStringBuilder(eventTitle).apply {
//            setSpan(BackgroundColorSpan(Color.RED), 0, title.length, SPAN_EXCLUSIVE_EXCLUSIVE)
//            setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, title.length, SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StrikethroughSpan(), 0, eventTitle.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return EventModel(
                id = id,
                title = spannableTitle,
                startTime = startTime,
                endTime = endTime,
                location = "Location $id",
                color = color,
                isAllDay = isAllDay,
                isCanceled = isCanceled
        )
    }
}