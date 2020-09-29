package com.hubwallet.apptspos.employes.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class EmpPinViewModel : ViewModel() {
    var liveData: LiveData<BaseResponse>? = null

    fun saveEmpPin(vendorId: String, stylist_id: String, pin: String) {
        liveData = ApiClient.getApiClientIns().saveEmpPin(vendorId, stylist_id, pin)
    }

    fun editEmpPin(vendorId: String, stylist_id: String, pin: String) {
        liveData = ApiClient.getApiClientIns().editEmpPin(vendorId, stylist_id, pin)
    }
}
