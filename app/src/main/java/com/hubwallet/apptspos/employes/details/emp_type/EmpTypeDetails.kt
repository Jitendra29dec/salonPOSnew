package com.hubwallet.apptspos.employes.details.emp_type


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EmpTypeDetails(
        @SerializedName("emp_type_id")
        var empTypeId: String,
        @SerializedName("employee_type")
        var employeeType: String
) {
    override fun toString(): String {
        return employeeType
    }
}