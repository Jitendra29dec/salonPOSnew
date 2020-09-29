package com.hubwallet.apptspos.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.Utils.Models.CustomerDataList
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.employes.details.state.StateListRes
import com.hubwallet.apptspos.retrofit.ApiClient

class CustomerViewModel : ViewModel() {
    var liveDataState: LiveData<StateListRes>? = null
    fun getState(countryId: String) {
        liveDataState = ApiClient.getApiClientIns().getState(countryId)
    }

    var liveDataAddCust: LiveData<BaseResponse>? = null

    fun addCustomer(vendorId: String,
                    firstname: String,
                    lastname: String,
                    email: String,
                    mobile: String,
                    address: String,
                    zipcode: String,
                    city_id: String,
                    state_id: String,
                    cc_email: String,
                    birthday: String,
                    photo: String,
                    gender: String,
                    emergency_name: String,
                    emergency_relation: String,
                    emergency_contact: String,
                    anniversary: String,
                    occupation: String,
                    referred_by: String,
                    iou_limit: String,
                    email_alert: String,
                    sms_alert: String,
                    push_notification: String,
                    allow_online_booking: String,
                    card_holder_name: String,
                    card_number: String,
                    cvv: String,
                    expiry_month: String,
                    expiry_year: String
    ) {
        liveDataAddCust = ApiClient.getApiClientIns().addCustomer(vendorId, firstname, lastname, email, mobile, address, zipcode, city_id, state_id, cc_email, birthday, photo, gender, emergency_name, emergency_relation, emergency_contact, anniversary, occupation, referred_by, iou_limit, email_alert, sms_alert, push_notification, allow_online_booking,
        card_holder_name,card_number,cvv,expiry_month,expiry_year)
    }

    var liveDataEditCust: LiveData<BaseResponse>? = null


    fun editCustomer(vendorId: String,
                     customerId: String,
                     firstname: String,
                     lastname: String,
                     email: String,
                     mobile: String,
                     address: String,
                     zipcode: String,
                     city_id: String,
                     state_id: String,
                     cc_email: String,
                     birthday: String,
                     photo: String,
                     gender: String,
                     emergency_name: String,
                     emergency_relation: String,
                     emergency_contact: String,
                     anniversary: String,
                     occupation: String,
                     referred_by: String,
                     iou_limit: String,
                     email_alert: String,
                     sms_alert: String,
                     push_notification: String,
                     allow_online_booking: String,
                     card_holder_name: String,
                     card_number: String,
                     cvv: String,
                     expiry_month: String,
                     expiry_year: String
    ) {
        liveDataEditCust = ApiClient.getApiClientIns().editCustomer(vendorId, customerId, firstname, lastname, email, mobile, address, zipcode, city_id, state_id, cc_email, birthday, photo, gender, emergency_name, emergency_relation, emergency_contact, anniversary, occupation, referred_by, iou_limit, email_alert, sms_alert, push_notification, allow_online_booking,
        card_holder_name,card_number,cvv,expiry_month,expiry_year)
    }

    var liveDataCustDetails: LiveData<CustomerDataList>? = null
    fun getCustDetails(customerId: String) {
        liveDataCustDetails = ApiClient.getApiClientIns().getCustomerDetails(customerId)
    }
}
