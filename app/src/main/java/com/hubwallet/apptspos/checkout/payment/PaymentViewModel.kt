package com.hubwallet.apptspos.checkout.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.checkout.disscount_res.produc.ProdcutDisscountREs
import com.hubwallet.apptspos.retrofit.ApiClient

class PaymentViewModel : ViewModel() {
    var liveDataCustReward: LiveData<RewardRes>? = null
    fun getReward(customer_id: String) {
        liveDataCustReward = ApiClient.getApiClientIns().getReward(customer_id)
    }

    var liveDataCoupon: LiveData<RewardRes>? = null
    fun checkCuppon(cupponCode: String) {
        liveDataCoupon = ApiClient.getApiClientIns().checkCuppon(cupponCode)
    }

    var liveDataCheckNumber: LiveData<RewardRes>? = null
    fun checkNumber(type: String, number: String) {
        liveDataCheckNumber = ApiClient.getApiClientIns().checkNumber(type, number)
    }

    var liveDataUpdateDepositStatus: LiveData<BaseResponse>? = null
    fun updateDeposit(depositId: String) {
        liveDataUpdateDepositStatus = ApiClient.getApiClientIns().updateDeposit(depositId = depositId)
    }

    var liveDataOrderNow: LiveData<BaseResponse>? = null
    fun orderNow(
            customerId: String,
            appointment_id: String,
            token: String,
            payment_type: String,
            tax: String,
            tip_amount: String,
            cash_value: String,
            credit_value: String,
            gift_card_value: String,
            certificate_value: String,
            new_gift_card: String,
            new_certificate: String,
            iou_value: String,
            package_amount: String,
            membership_val: String,
            discount_value: String,
            giftcard_db_value: String,
            gift_card_number: String,
            cash_mode_gift: String,
            diposite: String,
            total_amount: String,
            gift_certificate_id: String,
            gift_cart_id: String,
            service_total_price: String,
            vendor_id : String
    ) {
        liveDataOrderNow = ApiClient.getApiClientIns().orderNow(customerId, appointment_id, token, payment_type, tax, tip_amount, cash_value, credit_value, gift_card_value, certificate_value, new_gift_card, new_certificate, iou_value, package_amount, membership_val, discount_value, giftcard_db_value, gift_card_number, cash_mode_gift, diposite, total_amount, gift_certificate_id, gift_cart_id,service_total_price,vendor_id)
    }

}