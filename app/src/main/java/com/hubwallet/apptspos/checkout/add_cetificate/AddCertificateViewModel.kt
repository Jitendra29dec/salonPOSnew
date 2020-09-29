package com.hubwallet.apptspos.checkout.add_cetificate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateDataRes
import com.hubwallet.apptspos.checkout.add_cetificate.templet_res.TempletData
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.retrofit.ApiClient

class AddCertificateViewModel : ViewModel() {
    lateinit var liveDateTemplates: LiveData<TempletData>
    fun getTemplate(type: String) {
        liveDateTemplates = ApiClient.getApiClientIns().getTemplates(type)
    }

    lateinit var liveDateTemplatesData: LiveData<TemplateDataRes>
    fun getTemplateData(type: String) {
        liveDateTemplatesData = ApiClient.getApiClientIns().getTemplateData(type)
    }

    lateinit var liveDataAdd: LiveData<BaseResponse>

    fun addCertificate(customerId: String,
                       ceritificateNumber: String,
                       service_id: String,
                       gift_type: String,
                       expire_date: String,
                       gift_amount: String,
                       notifyBy: String,
                       select_recipient_name: String,
                       gift_recipient_name: String,
                       gift_recipient_email: String,
                       messasge: String,
                       template_id: String,
                       gift_recipient_phone: String,
                       template_image_id: String,
                       vendor_id: String,
                       login_id: String
    ) {
        liveDataAdd = ApiClient.getApiClientIns().addCertificate(customerId, ceritificateNumber, service_id, gift_type, expire_date, gift_amount, notifyBy, select_recipient_name,
                gift_recipient_name, gift_recipient_email, messasge, template_id, gift_recipient_phone, template_image_id, vendor_id, login_id)
    }

    var liveDataTitle: LiveData<ServiceDataRes>? = null
    fun getService(vendorId: String) {
        liveDataTitle = ApiClient.getApiClientIns().getService(vendorId)
    }

    var liveDataCustDetails: LiveData<CustDetailsRes>? = null
    fun getCustomerList(vendorId: String) {
        liveDataCustDetails = ApiClient.getApiClientIns().getCustomerList(vendorId)
    }

}