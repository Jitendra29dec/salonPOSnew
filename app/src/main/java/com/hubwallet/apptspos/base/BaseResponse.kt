package com.hubwallet.apptspos.base


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
open  class BaseResponse{
    @SerializedName("message")
    var message: String=""
    @SerializedName("status")
    var status: Int=0
    @SerializedName("stylist_id")
    var stylistId: String=""
    @SerializedName("desposit_id")
    var despositId: String=""
}

