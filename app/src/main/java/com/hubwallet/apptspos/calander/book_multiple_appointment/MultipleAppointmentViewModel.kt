package com.hubwallet.apptspos.calander.book_multiple_appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistData
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.retrofit.ApiClient

class MultipleAppointmentViewModel : ViewModel() {
    var liveDataTitle: LiveData<ServiceDataRes>? = null
    fun getService(vendorId: String) {
        liveDataTitle = ApiClient.getApiClientIns().getService(vendorId)
    }
    var liveDataStyliest: LiveData<StylistData>? = null


    fun getStylist(vendorId: String) {
        liveDataStyliest = ApiClient.getApiClientIns().getStylist(vendorId)
    }

    var liveData: LiveData<BaseResponse>? = null
    fun addMultipleAppoitment(vendorId: String,
                              appointment_date: String, //comma string
                              appointment_time: String,//comma string
                              service_id: String,//comma string
                              stylist_id: String,//comma string
                              duration: String,//comma string
                              note: String,
                              customer_id: String) {
        liveData = ApiClient.getApiClientIns().addMultipleAppoiment(vendorId, appointment_date, appointment_time, service_id, stylist_id, duration, note, customer_id)
    }
}