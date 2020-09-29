package com.hubwallet.apptspos.customer.cust_details


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CustomerDetails(
        @SerializedName("customer_id")
         var customerId: String="",
        @SerializedName("email")
        var email: String="",
        @SerializedName("firstname")
        var firstname: String="",
        @SerializedName("lastname")
        var lastname: String="",
        @SerializedName("mobile")
        var mobile: String="",
        @SerializedName("address")
        var address: String="",
        @SerializedName("zipcode")
        var zipcode: String="",
        @SerializedName("city")
        var city: String="",
        @SerializedName("birthday")
        var birthday: String="",
        @SerializedName("note")
        var note: String="",
        @SerializedName("anniversary")
        var anniversary: String="",
        @SerializedName("cc_appointment_email")
        var ccAppointmentEmail: String="",
        @SerializedName("gender")
        var gender: String="",
        @SerializedName("state_id")
        var stateId: String="",
        @SerializedName("customer_name")
        var customerName: String=""
/*@SerializedName("firstname")
var firstname: String,

@SerializedName("lastname")
var lastname: String,

@SerializedName("address")
var address: String,

@SerializedName("birthday")
var birthday: String,

@SerializedName("city")
var city: String,

@SerializedName("customer_id")
var customerId: String,


@SerializedName("email")
var email: String,

@SerializedName("mobile")
var mobile: String,

@SerializedName("note")
var note: String,

@SerializedName("pincode")
var pincode: String*/
) {
    override fun toString(): String {

        return customerName.split("\n").toTypedArray()[0]
    }
}