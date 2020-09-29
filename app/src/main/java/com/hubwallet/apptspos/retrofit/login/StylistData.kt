package com.hubwallet.apptspos.retrofit.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StylistData(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("styl ist_name")
    var stylIstName: String = "",
    @SerializedName("stylist_id")
    var stylistId: String = "",
    @SerializedName("stylist_name")
    var stylistName: String = ""
)