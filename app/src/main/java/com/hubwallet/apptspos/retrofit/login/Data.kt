package com.hubwallet.apptspos.retrofit.login


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("alternate_phone")
    var alternatePhone: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("last_login")
    var lastLogin: String = "",
    @SerializedName("login_id")
    var loginId: String = "",
    @SerializedName("owner_name")
    var ownerName: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("photo")
    var photo: String = "",
    @SerializedName("username")
    var username: String = "",
    @SerializedName("vendor_id")
    var vendorId: String = "",
    @SerializedName("vendor_name")
    var vendorName: String = ""
)