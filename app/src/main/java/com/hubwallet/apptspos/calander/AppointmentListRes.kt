package com.hubwallet.apptspos.calander


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.base.BaseResponse

@Keep
data class AppointmentListRes(
        @SerializedName("result")
        var result: ArrayList<Result> = ArrayList<Result>(),
        @SerializedName("deposit")
        var depositList: ArrayList<DepositDetails> = ArrayList<DepositDetails>()
) : BaseResponse()