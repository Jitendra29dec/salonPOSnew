package com.hubwallet.apptspos.calander.change_Status


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ChangeStatusRes(
    @SerializedName("getData")
    var getData: List<GetData> = listOf(),
    @SerializedName("message")
    var message: String = ""
)