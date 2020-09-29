package com.hubwallet.apptspos.employes.service


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class ServiceDetails(
    @SerializedName("category_name")
    var categoryName: String,
    @SerializedName("duration")
    var duration: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("service_id")
    var serviceId: String,
    @SerializedName("service_name")
    var serviceName: String,
    @SerializedName("sku")
    var sku: String ,
    @SerializedName("active")
    var active: Boolean
){
    override fun toString(): String {
        return serviceName
    }
}