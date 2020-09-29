package com.hubwallet.apptspos.checkout

import com.google.gson.annotations.SerializedName

data class Product(
        @SerializedName("barcode_id")
        val barcode_id: String,
        @SerializedName("cart_id")
        val cart_id: String,
        @SerializedName("price_retail")
        val price_retail: Double,
        var priceWithQty: Double,
        @SerializedName("pro_quant")
        val pro_quant: String,
        @SerializedName("product_name")
        val product_name: String,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("sku")
        val sku: String,
        @SerializedName("tax_amount")
        val tax_amount: Double,
        @SerializedName("tax_rate")
        val tax_rate: String,
        @SerializedName("product_id")
        val product_id: String,
        var selectedDisscountPosition: Int = 0,
        var finalAmount: Double,
        var discountAmount:Double,
        var afterDisscunt:Double
)