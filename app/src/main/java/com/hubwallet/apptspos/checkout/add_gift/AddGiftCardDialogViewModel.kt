package com.hubwallet.apptspos.checkout.add_gift

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateDataRes
import com.hubwallet.apptspos.checkout.add_cetificate.templet_res.TempletData
import com.hubwallet.apptspos.retrofit.ApiClient

class AddGiftCardDialogViewModel : ViewModel() {
    lateinit var liveDateTemplates: LiveData<TempletData>

    fun getTemplate(type: String) {
        liveDateTemplates = ApiClient.getApiClientIns().getTemplates(type)
    }

    lateinit var liveDateTemplatesData: LiveData<TemplateDataRes>

    fun getTemplateData(type: String) {
        liveDateTemplatesData = ApiClient.getApiClientIns().getTemplateData(type)
    }

    lateinit var liveDateTAddCard: LiveData<BaseResponse>

    fun addGiftCard(customerId: String,
                    gift_card_number: String,
                    card_issue_date: String,
                    card_buyer_name: String,
                    card_buyer_email: String,
                    card_phone: String,
                    card_message: String,
                    card_amount: String,
                    notifyBy_card: String,
                    template_image_id: String,
                    vendor_id: String,
                    login_id: String
    ) {
        liveDateTAddCard = ApiClient.getApiClientIns().addGiftCard(customerId, gift_card_number, card_issue_date, card_buyer_name, card_buyer_email, card_phone, card_message,
                card_amount, notifyBy_card, template_image_id, vendor_id, login_id)
    }
}