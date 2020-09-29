package com.hubwallet.apptspos.retrofit.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PinLoginRes(
    @SerializedName("customer_data")
    var customerData: List<CustomerData> = listOf(),
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("service_data")
    var serviceData: List<ServiceData> = listOf(),
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("stylist_data")
    var stylistData: List<StylistData> = listOf()
)