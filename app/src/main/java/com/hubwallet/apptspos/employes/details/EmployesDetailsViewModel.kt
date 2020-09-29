package com.hubwallet.apptspos.employes.details

import EmployeeList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.employes.details.emp_type.EmpType
import com.hubwallet.apptspos.employes.details.state.StateListRes
import com.hubwallet.apptspos.employes.details.title.TitleRes
import com.hubwallet.apptspos.retrofit.ApiClient

class EmployesDetailsViewModel : ViewModel() {
    var liveDataType: LiveData<EmpType>? = null
    fun getEmployeeType(vendorId: String) {
        liveDataType = ApiClient.getApiClientIns().getEmployeeType(vendorId)
    }

    var liveDataTitle: LiveData<TitleRes>? = null
    fun getEmployeeTitle(vendorId: String) {
        liveDataTitle = ApiClient.getApiClientIns().getEmployeeTitle(vendorId)
    }

    var liveDataState: LiveData<StateListRes>? = null
    fun getState(countryId: String) {
        liveDataState = ApiClient.getApiClientIns().getState(countryId)
    }


    var liveDataAddEmp: LiveData<BaseResponse>? = null
    fun addEmployee(vendorId: String,
                    emp_type_id: String,
                    title_id: String,
                    firstname: String,
                    lastname: String,
                    email: String,
                    phone: String,
                    alternate_phone: String,
                    state_id: String,
                    city: String,
                    address: String,
                    photo: String) {
        liveDataAddEmp = ApiClient.getApiClientIns().addEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo)
    }

    var liveDataEditEmp: LiveData<BaseResponse>? = null
    fun editEmployee(vendorId: String,
                     emp_type_id: String,
                     title_id: String,
                     firstname: String,
                     lastname: String,
                     email: String,
                     phone: String,
                     alternate_phone: String,
                     state_id: String,
                     city: String,
                     address: String,
                     photo: String,
                     stylist_id: String) {
        liveDataEditEmp = ApiClient.getApiClientIns().editEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo, stylist_id)
    }

    var liveDataEmpDetails: LiveData<EmployeeList>? = null
    fun getEmpDetails(vendorId: String,customerId: String) {
        liveDataEmpDetails = ApiClient.getApiClientIns().getEmployeeDetails(vendorId,customerId)
    }
    }

