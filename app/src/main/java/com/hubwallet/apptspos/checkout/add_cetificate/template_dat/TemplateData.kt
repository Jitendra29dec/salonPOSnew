package com.hubwallet.apptspos.checkout.add_cetificate.template_dat


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
 class TemplateData {
    @SerializedName("date_created")
    var dateCreated: String = ""

    @SerializedName("id")
    var id: String = ""

    @SerializedName("image")
    var image: String = ""

    @SerializedName("status")
    var status: String = ""

    @SerializedName("template_id")
    var templateId: String = ""

    var isChecked=false
}