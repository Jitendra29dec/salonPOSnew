package com.hubwallet.apptspos.employes.details.title


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EmpTitle(
        @SerializedName("title")
        var title: String,
        @SerializedName("title_id")
        var titleId: String
) {
    override fun toString(): String {
        return title
    }
}