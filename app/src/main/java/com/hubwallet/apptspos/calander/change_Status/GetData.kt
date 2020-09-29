package com.hubwallet.apptspos.calander.change_Status


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GetData(
    @SerializedName("color_code")
    var colorCode: String = "#FFFFFF",
    @SerializedName("color_id")
    var colorId: String = "",
    @SerializedName("name")
    var name: String = ""
)