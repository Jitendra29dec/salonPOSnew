package com.hubwallet.apptspos.base


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LockScreenRes(
    @SerializedName("active")
    var active: Int = 0,
    @SerializedName("locktime")
    var locktime: Int = 0,
    @SerializedName("warning_time")
    var warningTime: Int = 0
):BaseResponse()