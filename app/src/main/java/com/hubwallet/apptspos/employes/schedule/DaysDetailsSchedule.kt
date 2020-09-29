package com.hubwallet.apptspos.employes.schedule


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DaysDetailsSchedule(
    @SerializedName("active")
    var active: Boolean,
    @SerializedName("days")
    var days: String,
    @SerializedName("from")
    var from: String,
    @SerializedName("to")
    var to: String
)