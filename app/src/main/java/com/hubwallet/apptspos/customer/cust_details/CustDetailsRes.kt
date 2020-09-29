package com.hubwallet.apptspos.customer.cust_details


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.hubwallet.apptspos.base.BaseResponse

@Keep
 data class CustDetailsRes(
    @SerializedName("result")
    var result: ArrayList<CustomerDetails> =ArrayList<CustomerDetails>()
) : BaseResponse()
