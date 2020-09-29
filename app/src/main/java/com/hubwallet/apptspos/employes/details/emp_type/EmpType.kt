package com.hubwallet.apptspos.employes.details.emp_type


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.hubwallet.apptspos.base.BaseResponse

@Keep
 class EmpType:BaseResponse(){
    @SerializedName("result")
    var result= ArrayList<EmpTypeDetails>()
}