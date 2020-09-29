package com.hubwallet.apptspos.checkout.disscount_res.produc


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.checkout.disscount_res.service.ServiceDicountDetails

@Keep
data class ProdcutDisscountREs(
        @SerializedName("message")
        var message: String = "",
        @SerializedName("products")
        var products: List<ServiceDicountDetails> = listOf(),
        @SerializedName("status")
        var status: Int = 0
)