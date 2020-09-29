package com.hubwallet.apptspos.calander


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DepositDetails(
    @SerializedName("color")
    var color: String,
    @SerializedName("customer_name")
    var customerName: String,
    @SerializedName("end")
    var end: String,
    @SerializedName("rendering")
    var rendering: String,
    @SerializedName("start")
    var start: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("token1")
    var token1: String,
    @SerializedName("token2")
    var token2: String,
    @SerializedName("customer_id")
    var customerId: String
)