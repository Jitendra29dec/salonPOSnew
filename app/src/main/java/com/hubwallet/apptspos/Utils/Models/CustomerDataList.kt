package com.hubwallet.apptspos.Utils.Models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.base.BaseResponse

@Keep
 data class CustomerDataList (
        @SerializedName("result")
        var result: CustomerData?
) : BaseResponse()