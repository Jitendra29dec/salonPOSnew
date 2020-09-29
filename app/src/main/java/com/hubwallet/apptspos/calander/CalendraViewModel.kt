package com.hubwallet.apptspos.calander

import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.calander.filter.SearchRes
import com.hubwallet.apptspos.retrofit.ApiClient

class CalendraViewModel:ViewModel() {

    var liveData: LiveData<SearchRes>? = null
    fun searchAppointment(vendorId: String,searchVal: String) {
        liveData = ApiClient.getApiClientIns().searchAppointment(vendorId,searchVal)
    }
}