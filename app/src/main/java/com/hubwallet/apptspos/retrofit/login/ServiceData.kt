package com.hubwallet.apptspos.retrofit.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ServiceData(
    @SerializedName("service_id")
    var serviceId: String = "",
    @SerializedName("service_name")
    var serviceName: String = ""
)