package com.hubwallet.apptspos.checkout

import com.google.gson.annotations.SerializedName

class CheckoutRes {
    @SerializedName("certificate")
    lateinit var certificate: List<Certificate>

    //     lateinit var customer_membership: List<Any>
//     lateinit var customer_packages: List<Any>
    @SerializedName("gift_card")
    lateinit var gift_card: List<GiftCard>

    //     lateinit var iou_data: List<Any>
    lateinit var message: String

    @SerializedName("product")
    lateinit var product: List<Product>

    @SerializedName("services")
    lateinit var services: List<Service>
    var status: Int = 0
}

