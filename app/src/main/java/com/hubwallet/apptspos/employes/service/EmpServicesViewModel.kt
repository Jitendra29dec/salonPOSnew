package com.hubwallet.apptspos.employes.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class EmpServicesViewModel : ViewModel() {
    var liveDataTitle: LiveData<ServiceDataRes>? = null
    fun getService(vendorId: String) {
        liveDataTitle = ApiClient.getApiClientIns().getService(vendorId)
    }

    var liveData: LiveData<BaseResponse>? = null

    fun addEmpService(vendorId: String, stylist_id: String, days: String) {
        liveData = ApiClient.getApiClientIns().addEmpService(vendorId, stylist_id, days)
    }

    fun editEmpService(vendorId: String, stylist_id: String, days: String) {
        liveData = ApiClient.getApiClientIns().editEmpService(vendorId, stylist_id, days)
    }
}
