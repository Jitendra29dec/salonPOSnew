package com.hubwallet.apptspos.calander.book_multiple_appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.retrofit.ApiClient

class SubmitMultipleViewModel : ViewModel() {

    var liveDataCustDetails: LiveData<CustDetailsRes>? = null
    fun getCustomerList(vendorId: String) {
        liveDataCustDetails = ApiClient.getApiClientIns().getCustomerList(vendorId)
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
