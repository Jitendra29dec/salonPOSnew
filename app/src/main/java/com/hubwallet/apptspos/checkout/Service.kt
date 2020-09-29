package com.hubwallet.apptspos.checkout

import com.google.gson.annotations.SerializedName

data class Service(
        @SerializedName("appointment_date")
        val appointment_date: String,
        @SerializedName("appointment_id")
        val appointment_id: String,
        @SerializedName("appointment_time")
        val appointment_time: String,
        @SerializedName("as_id")
        val as_id: String,
        @SerializedName("customer_id")
        val customer_id: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("parent_id")
        val parent_id: String,
        @SerializedName("points")
        val points: Any,
        @SerializedName("price")
        val price: Double,
        @SerializedName("service_id")
        val service_id: String,
        @SerializedName("service_name")
        val service_name: String,
        @SerializedName("service_point")
        val service_point: String,
        @SerializedName("stylist_id")
        val stylist_id: String,
        @SerializedName("stylist_name")
        val stylist_name: String,
        @SerializedName("tax_amount")
        val tax_amount: Double,
        @SerializedName("tax_rate")
        val tax_rate: Double,
        @SerializedName("tip")
        val tip: String,
        var selectedDisscountPosition: Int,
        var finalAmount: Double,
        var discountAmount: Double,
        var afterDisscunt: Double

) {
    override fun toString(): String {
        return appointment_id
    }
}