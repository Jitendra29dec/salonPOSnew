package com.hubwallet.apptspos.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.retrofit.ApiClient
import com.hubwallet.apptspos.retrofit.login.PinLoginRes

class BaseViewModel : ViewModel() {

    var pinLiveData: LiveData<BaseResponse>? = null
    fun checkPin(pin: String) {
        pinLiveData = ApiClient.getApiClientIns().checkPin(pin)
    }

     var pinLoginLiveData: LiveData<PinLoginRes>? = null
    fun loginPin(pin: String, fcm_token:String) {
        pinLoginLiveData = ApiClient.getApiClientIns().loginPin(pin,fcm_token)
    }

    var screenLockTimeData: LiveData<LockScreenRes>? = null
    fun screenLockTime(pin: String) {
        screenLockTimeData = ApiClient.getApiClientIns().screenLockTime(pin)
    }
}