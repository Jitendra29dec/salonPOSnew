package com.hubwallet.apptspos.checkout.add_product


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Product(
    @SerializedName("barcode_id")
    var barcodeId: String = "",
    @SerializedName("category_name")
    var categoryName: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("price_retail")
    var priceRetail: String = "",
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("product_image")
    var productImage: String = "",
    @SerializedName("product_name")
    var productName: String = "",
    @SerializedName("quantity")
    var quantity: String = "",
    @SerializedName("sku")
    var sku: String = "",
    @SerializedName("brand_name")
    var brandName: String = "",
    var purchageQuatity: String = ""
)