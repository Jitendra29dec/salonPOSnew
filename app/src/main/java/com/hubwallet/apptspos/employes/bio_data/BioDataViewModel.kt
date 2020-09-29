package com.hubwallet.apptspos.employes.bio_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class BioDataViewModel : ViewModel() {
    var liveData: LiveData<BaseResponse>? = null

    fun addBioData(vendorId: String,
                    stylist_id: String,
                    experienceYear: String,
                    experienceMonth: String,
                    workLocation: String,
                    note: String,
                    services: String) {
        liveData = ApiClient.getApiClientIns().addBioData(vendorId, stylist_id, experienceYear, experienceMonth, workLocation, note, services)
    }

    fun editBioData(vendorId: String,
                   stylist_id: String,
                   experienceYear: String,
                   experienceMonth: String,
                   workLocation: String,
                   note: String,
                   services: String) {
        liveData = ApiClient.getApiClientIns().editBioData(vendorId, stylist_id, experienceYear, experienceMonth, workLocation, note, services)
    }
}
