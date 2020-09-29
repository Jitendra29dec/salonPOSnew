package com.hubwallet.apptspos.calander


import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.view.week_view.WeekViewDisplayable
import com.hubwallet.apptspos.view.week_view.WeekViewEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class Result(
        @SerializedName("appointment_type")
        var appointmentType: Int,
        @SerializedName("backgroundColor")
        var backgroundColor: String,
        @SerializedName("color")
        var color: String,
        @SerializedName("color_id")
        var colorId: String,
        @SerializedName("date")
        var date: String,
        @SerializedName("duration")
        var duration: Int,
        @SerializedName("is_checkout")
        var isCheckOut: Int,
        @SerializedName("id")
        var id: Long,
        @SerializedName("rendering")
        var rendering: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("token_no")
        var tokenNo: String,
        var isCanceled: Boolean,

        @SerializedName("customer_id")
        var customerId: String

) : WeekViewDisplayable<Result> {

    override fun toWeekViewEvent(): WeekViewEvent<Result> {
        if (date.contains("null", true)) {
            date.replace("null", "12:45")
        }
//        val backgroundColor = if (!isCanceled) color else Color.WHITE
//        val textColor = if (!isCanceled) Color.WHITE else Color.BLACK
        val borderWidthResId = if (!isCanceled) R.dimen.no_border_width else R.dimen.border_width
        val startTime = Calendar.getInstance()
        val outputformat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

        val output = outputformat.parse(date)!!
        Log.d("calander", outputformat.format(output))
        Log.d("calander", "${startTime.get(Calendar.YEAR)}" +
                ",${startTime.get(Calendar.MONTH)}" +
                ",${startTime.get(Calendar.DAY_OF_MONTH)}" +
                ",${startTime.get(Calendar.HOUR)}" +
                ",${startTime.get(Calendar.MINUTE)}, ")
        startTime.time = output
        var newTitle:CharSequence=""
        if (title!=null){
            newTitle=  title.trim()
            newTitle = newTitle.replace("\\n+".toRegex(), "")
            newTitle = newTitle.replace("\\r+".toRegex(), "")
Log.d("titlePrint",newTitle)
        }else{
            newTitle="demo event "
        }
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.MINUTE, duration)

        val style = WeekViewEvent.Style.Builder()
                .setTextColor(Color.parseColor("#FFFFFF"))
                .setBackgroundColor(Color.parseColor(backgroundColor))
                .setTextStrikeThrough(isCanceled)
                .setBorderWidthResource(borderWidthResId)
                .setBorderColor(Color.parseColor("#AAAAAA"))
                .build()
        val styledTitle = SpannableStringBuilder(newTitle).apply {
            setSpan(StyleSpan(Typeface.NORMAL), 0, newTitle.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            setSpan(StrikethroughSpan(), 0, newTitle.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return WeekViewEvent.Builder(this)
                .setId(id)
                .setTitle(styledTitle)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setLocation("")
                .setAllDay(false)
                .setStyle(style)
                .build()
    }
}
