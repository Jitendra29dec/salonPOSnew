package com.hubwallet.apptspos.checkout.payment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RewardRes(
    @SerializedName("amount")
    var amount: Double = 0.0,
    @SerializedName("status")
    var status: Int = 0
)