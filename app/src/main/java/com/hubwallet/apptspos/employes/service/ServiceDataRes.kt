package com.hubwallet.apptspos.employes.service


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.hubwallet.apptspos.base.BaseResponse

@Keep
data class ServiceDataRes(
    @SerializedName("result")
    var result: ArrayList<ServiceDetails> =ArrayList<ServiceDetails>()
):BaseResponse()