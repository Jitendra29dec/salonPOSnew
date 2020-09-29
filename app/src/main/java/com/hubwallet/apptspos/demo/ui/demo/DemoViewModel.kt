package com.hubwallet.apptspos.demo.ui.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.calander.AppointmentListRes
import com.hubwallet.apptspos.retrofit.ApiClient

class DemoViewModel : ViewModel() {
    var liveData: LiveData<AppointmentListRes>? = null
    fun getAppointement(vendorId: String ) {
        liveData= ApiClient.getApiClientIns().getAppointement(vendorId)
    }
}
