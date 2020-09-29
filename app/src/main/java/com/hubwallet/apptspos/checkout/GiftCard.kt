package com.hubwallet.apptspos.checkout

data class GiftCard(
    val amount: Double,
    val buyer_email: String,
    val buyer_name: String,
    val card_id: String,
    val card_number: String,
    val created_date: String,
    val customer_id: String,
    val intial_amount: String,
    val is_active: String,
    val issue_date: String,
    val message: String,
    val modified_date: Any,
    val notifyby: String,
    val phone: String,
    val template_id: String,
    val template_image_id: String,
    val vendor_id: String
)