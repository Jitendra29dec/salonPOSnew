package com.hubwallet.apptspos.Utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {
    companion object {
        fun change24to12hours(time: String): String {
            val sdf = SimpleDateFormat("HH:mm")
            var convertedTime = ""
            try {
                val date3: Date = sdf.parse(time)
                val sdf2 = SimpleDateFormat("hh:mm aa")
                convertedTime = sdf2.format(date3)
                Log.d("convertedTime", convertedTime)
                System.out.println("Given time in AM/PM: " + sdf2.format(date3))
            } catch (e: ParseException) {
                e.printStackTrace()
                Log.d("convertedTime", e.localizedMessage)
            }
            return convertedTime
        }

        fun differenceTwoDate(dateStart: String, dateEnd: String): Int {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm");

            val date1 = simpleDateFormat.parse(dateStart)
            val date2 = simpleDateFormat.parse(dateEnd)
            val diff: Long = date2!!.getTime() - date1!!.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            return minutes.toInt()
        }
    }
}