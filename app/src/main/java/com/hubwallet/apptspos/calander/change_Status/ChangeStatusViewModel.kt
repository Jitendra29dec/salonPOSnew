package com.hubwallet.apptspos.calander.change_Status

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.checkout.payment.RewardRes
import com.hubwallet.apptspos.retrofit.ApiClient

class ChangeStatusViewModel : ViewModel() {

   var liveDataOptions: LiveData<ChangeStatusRes>? = null
  fun getOptionsStatus(vendorId: String) {
        liveDataOptions = ApiClient.getApiClientIns().getOptionsStatus(vendorId)
    }

    var liveDataChangeStatus: LiveData<ChangeStatusRes>? = null
  fun changeStatus(type: String,appiointmentId:String,colorId:String) {
      liveDataChangeStatus = ApiClient.getApiClientIns().changeStatus(type,appiointmentId,colorId)
    }
}