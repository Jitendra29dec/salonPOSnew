package com.hubwallet.apptspos.checkout.add_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class AddProductViewModel : ViewModel() {
    lateinit var liveDataAdd: LiveData<ProductRes>

    fun getProduct(
            vendor_id: String
    ) {
        liveDataAdd = ApiClient.getApiClientIns().getProduct(vendor_id)
    }

    lateinit var liveDataAddProdct: LiveData<BaseResponse>

    fun addProdcut(product_id: String,
                   quant: String,
                   customer_id: String
    ) {
        liveDataAddProdct = ApiClient.getApiClientIns().addCheckoutproduct(product_id, quant, customer_id)
    }
}