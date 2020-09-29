package com.hubwallet.apptspos.employes.details.state


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StateDetails(
        @SerializedName("state_id")
        var stateId: String,
        @SerializedName("state_name")
        var stateName: String
) {
    override fun toString(): String {
        return stateName
    }
}