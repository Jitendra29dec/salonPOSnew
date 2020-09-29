package com.hubwallet.apptspos.calander.add_deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.retrofit.ApiClient

class AddDepositViewModel : ViewModel() {
    var liveDataCustDetails: LiveData<CustDetailsRes>? = null
    fun getCustomerList(vendorId: String) {
        liveDataCustDetails = ApiClient.getApiClientIns().getCustomerList(vendorId)
    }

    var liveDataDepositDetails: LiveData<CustDetailsRes>? = null
    fun getDepositDetails(depositId: String) {
        liveDataDepositDetails = ApiClient.getApiClientIns().getCustomerList(depositId)
    }

    var liveDataUpdateDeposit: LiveData<BaseResponse>? = null
    fun updateDeposit(depositId: String,
                      cust_id: String,
                      vendorId: String,
                      customer_total: String,
                      deposit_amount: String,
                      pool: String,
                      overdue: String,
                      iou_limit: String,
                      chargeAuto: String,
                      event_name: String,
                      deposit_type: String,
                      event_note: String,
                      start_date: String,
                      start_time: String,
                      end_time: String
    ) {
        liveDataUpdateDeposit = ApiClient.getApiClientIns().updateDeposit(depositId, cust_id, vendorId, customer_total, deposit_amount, pool, overdue, iou_limit, chargeAuto, event_name, deposit_type, event_note, start_date, start_time, end_time)
    }

    var liveDataAddDeposit: LiveData<BaseResponse>? = null
    fun addDeposit(cust_id: String,
                   vendorId: String,
                   customer_total: String,
                   deposit_amount: String,
                   pool: String,
                   overdue: String,
                   iou_limit: String,
                   chargeAuto: String,
                   event_name: String,
                   deposit_type: String,
                   event_note: String,
                   start_date: String,
                   start_time: String,
                   end_time: String
    ) {
        liveDataAddDeposit = ApiClient.getApiClientIns().addDeposit(cust_id, vendorId, customer_total, deposit_amount, pool, overdue, iou_limit, chargeAuto, event_name, deposit_type, event_note, start_date, start_time, end_time)
    }

    var liveDataUpdateDepositStatus: LiveData<BaseResponse>? = null
    fun updateDeposit(depositId: String) {
        liveDataUpdateDepositStatus = ApiClient.getApiClientIns().updateDeposit(depositId = depositId)
    }
}