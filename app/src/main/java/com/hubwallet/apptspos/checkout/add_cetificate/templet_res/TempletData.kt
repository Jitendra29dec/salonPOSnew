package com.hubwallet.apptspos.checkout.add_cetificate.templet_res


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
 class TempletData{
    @SerializedName("message")
    var message: String = ""
    @SerializedName("status")
     var status: Int = 0
    @SerializedName("template")
    var template: List<Template> = listOf()
}