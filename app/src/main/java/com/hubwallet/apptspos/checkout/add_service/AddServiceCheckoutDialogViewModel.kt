package com.hubwallet.apptspos.checkout.add_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistData
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.retrofit.ApiClient

class AddServiceCheckoutDialogViewModel : ViewModel() {
    var liveDataTitle: LiveData<ServiceDataRes>? = null
    fun getService(vendorId: String) {
        liveDataTitle = ApiClient.getApiClientIns().getService(vendorId)
    }

    var liveDataStyliest: LiveData<StylistData>? = null
    fun getStylist(vendorId: String) {
        liveDataStyliest = ApiClient.getApiClientIns().getStylist(vendorId)
    }



    var liveData: LiveData<BaseResponse>? = null
    fun checkoutAddService(customerId: String,
                           date: String,
                           token: String,
                           vendorId: String,
                           aptType: String,
                           appointmentTime: String,
                           duration: String,
                           service: String,
                           stylist1: String,
                           amount_service: String) {
        liveData = ApiClient.getApiClientIns().checkoutAddService(customerId, date, token, vendorId, aptType, appointmentTime, duration, service, stylist1, amount_service)
    }
}