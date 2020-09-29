package com.hubwallet.apptspos.checkout.add_cetificate.templet_res


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Template(
    @SerializedName("is_active")
    var isActive: String = "",
    @SerializedName("is_delete")
    var isDelete: String = "",
    @SerializedName("template_id")
    var templateId: String = "",
    @SerializedName("template_name")
    var templateName: String = "",
    @SerializedName("template_type")
    var templateType: String = ""
){
    override fun toString(): String {
        return templateName
    }
}