package com.hubwallet.apptspos.view.week_view

import android.content.Context
import android.graphics.Color
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.util.TypedValue.applyDimension

internal object Defaults {
    const val BACKGROUND_COLOR = Color.WHITE
    val PAST_BACKGROUND_COLOR = Color.rgb(227, 227, 227)
    val FUTURE_BACKGROUND_COLOR = Color.rgb(245, 245, 245)

    val EVENT_COLOR = Color.rgb(159, 198, 231)

    val GRID_COLOR = Color.rgb(102, 102, 102)
    const val NOW_COLOR = Color.BLACK
    val SEPARATOR_COLOR = Color.rgb(230, 230, 230)
    val HIGHLIGHT_COLOR = Color.rgb(39, 137, 228)

    fun textSize(context: Context): Int = convertTextDimension(context, 12)

    private fun convertTextDimension(context: Context, textSize: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return applyDimension(COMPLEX_UNIT_SP, textSize.toFloat(), displayMetrics).toInt()
    }
}
