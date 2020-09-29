package com.hubwallet.apptspos.checkout.add_product


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProductRes(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("result")
    var product: List<Product> = listOf(),
    @SerializedName("status")
    var status: Int = 0
)