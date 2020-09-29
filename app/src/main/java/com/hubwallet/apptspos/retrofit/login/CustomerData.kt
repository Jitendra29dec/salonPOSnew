package com.hubwallet.apptspos.retrofit.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CustomerData(
    @SerializedName("customer_id")
    var customerId: String = "",
    @SerializedName("customer_name")
    var customerName: String = "",
    @SerializedName("email")
    var email: String = ""
)