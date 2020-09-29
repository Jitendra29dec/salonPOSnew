package com.hubwallet.apptspos.employes.details.state


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.base.BaseResponse

@Keep
data class StateListRes(
        @SerializedName("result")
        var result: ArrayList<StateDetails> = ArrayList<StateDetails>()

) : BaseResponse()