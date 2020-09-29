package com.hubwallet.apptspos.employes.gallery

import Json4Kotlin_Base
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.retrofit.ApiClient

class GalleryDataViewModel : ViewModel() {

    var liveData: LiveData<BaseResponse>? = null

    var liveDataGalley: LiveData<Json4Kotlin_Base>? = null

    fun addstylishgallry(image: String,
                         stylist_id: String, image_name: String, type: String) {
        liveData = ApiClient.getApiClientIns().addstylishgallry(image, stylist_id, image_name, type)
    }

    fun getStylishgallery(stylist_id: String, type: String) {
        liveDataGalley = ApiClient.getApiClientIns().getstylishgallry(stylist_id, type)
    }
}