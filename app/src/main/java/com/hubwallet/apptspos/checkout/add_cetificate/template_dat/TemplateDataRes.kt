package com.hubwallet.apptspos.checkout.add_cetificate.template_dat


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TemplateDataRes(
    @SerializedName("image_url")
    var imageUrl: String = "",
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("template")
    var templateData: List<TemplateData> = listOf()
)