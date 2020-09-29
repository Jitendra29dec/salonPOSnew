package com.hubwallet.apptspos.employes.details.title


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.hubwallet.apptspos.base.BaseResponse

@Keep
data class TitleRes(
    @SerializedName("result")
    var result: ArrayList<EmpTitle> = ArrayList<EmpTitle>()
):BaseResponse()