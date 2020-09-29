package com.hubwallet.apptspos.Utils.Models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CustomerData(
        @SerializedName("customer_id")
        var customerId: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("firstname")
        var firstname: String,
        @SerializedName("lastname")
        var lastname: String,
        @SerializedName("mobile")
        var mobile: String,
        @SerializedName("address")
        var address: String,
        @SerializedName("zipcode")
        var zipcode: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("birthday")
        var birthday: String,
        @SerializedName("note")
        var note: String,
        @SerializedName("anniversary")
        var anniversary: String,
        @SerializedName("cc_appointment_email")
        var ccAppointmentEmail: String,

        @SerializedName("gender")
        var gender: String,
        @SerializedName("state_id")
        var stateId: String,
        @SerializedName("customer_name")
        var customerName: String,
        @SerializedName("emergency_contact")
        var emergency_contact: String,
        @SerializedName("emergency_relation")
        var emergency_relation: String,
        @SerializedName("emergency_contact_no")
        var emergency_contact_no: String,
        @SerializedName("occupation")
        var occupation: String,
        @SerializedName("iou_limit")
        var iou_limit: String,
        @SerializedName("card_holder_name")
        var card_holder_name: String,
        @SerializedName("card_number")
        var card_number: String,
        @SerializedName("cvv")
        var cvv: String,

        @SerializedName("expiry_month")
        var expiry_month: String,

        @SerializedName("expiry_year")
        var expiry_year: String

)