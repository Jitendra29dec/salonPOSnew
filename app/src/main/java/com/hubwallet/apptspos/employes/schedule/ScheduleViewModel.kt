package com.hubwallet.apptspos.employes.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class ScheduleViewModel : ViewModel() {
    var liveData: LiveData<BaseResponse>? = null

    fun addSchedule(vendorId: String, stylist_id: String, days: String) {
        liveData = ApiClient.getApiClientIns().addSchedule(vendorId, stylist_id, days)
    }

    fun editSchedule(vendorId: String, stylist_id: String, days: String) {
        liveData = ApiClient.getApiClientIns().editSchedule(vendorId, stylist_id, days)
    }
}
