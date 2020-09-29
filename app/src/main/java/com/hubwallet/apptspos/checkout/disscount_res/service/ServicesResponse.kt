package com.hubwallet.apptspos.checkout.disscount_res.service


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServicesResponse(
        @SerializedName("message")
        var message: String = "",
        @SerializedName("services")
        var services: List<ServiceDicountDetails> = listOf(),
        @SerializedName("status")
        var status: Int = 0
)