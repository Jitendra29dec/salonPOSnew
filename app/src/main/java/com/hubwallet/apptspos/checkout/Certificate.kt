package com.hubwallet.apptspos.checkout

data class Certificate(
    val amount: Double,
    val created_by: String,
    val created_date: String,
    val customer_id: String,
    val expire_on: String,
    val gift_certificate_no: String,
    val gift_id: String,
    val is_active: String,
    val is_delete: String,
    val message: String,
    val modified_by: Any,
    val notifyBy: String,
    val recipient_email: String,
    val recipient_name: String,
    val recipient_phone: String,
    val sender_name: String,
    val service_id: String,
    val service_type: String,
    val stylist_id: String,
    val template_id: String,
    val template_image_id: String,
    val vendor_id: String
)