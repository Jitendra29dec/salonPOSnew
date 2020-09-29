package com.hubwallet.apptspos.checkout.disscount_res.service


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServiceDicountDetails(
        @SerializedName("discount")
        var discount: String = "",
        @SerializedName("discount_id")
        var discountId: String = "",
        @SerializedName("discount_type")
        var discountType: String = ""
) {
    override fun toString(): String {
        return "$discount$discountType"
    }
}