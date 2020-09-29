package com.hubwallet.apptspos.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.calander.filter.SearchRes
import com.hubwallet.apptspos.checkout.disscount_res.produc.ProdcutDisscountREs
import com.hubwallet.apptspos.checkout.disscount_res.service.ServicesResponse
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.retrofit.ApiClient

class CheckoutViewModel : ViewModel() {
    var liveData: LiveData<CheckoutRes>? = null
    fun checkoutData(customerId: String,
                     appointmentId: String,
                     type: String,
                     tokenNo: String,
                     vendorId: String,
                     searchValue: String) {
        liveData = ApiClient.getApiClientIns().checkoutData(customerId, appointmentId, type, tokenNo, vendorId, searchValue)
    }

    var liveDataCustDetails: LiveData<CustDetailsRes>? = null
    fun getCustomerList(vendorId: String) {
        liveDataCustDetails = ApiClient.getApiClientIns().getCustomerList(vendorId)
    }

    var liveDataDelete: LiveData<BaseResponse>? = null

    fun deleteCheckoutRow(type: String, id: String) {
        liveDataDelete = ApiClient.getApiClientIns().deleteCheckoutRow(type, id)
    }

    var liveDataServiceDisscount: LiveData<ServicesResponse>? = null

    fun getServiceDisscunt(vendorId: String) {
        liveDataServiceDisscount = ApiClient.getApiClientIns().getServiceDiscount(vendorId)
    }

    var liveDataProductDisscount: LiveData<ProdcutDisscountREs>? = null

    fun getProductDiscount(vendorId: String) {
        liveDataProductDisscount = ApiClient.getApiClientIns().getProductDiscount(vendorId)
    }
}
