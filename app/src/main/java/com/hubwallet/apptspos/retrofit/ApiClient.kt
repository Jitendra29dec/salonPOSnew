package com.hubwallet.apptspos.retrofit

import EmployeeList
import Json4Kotlin_Base
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.hubwallet.apptspos.BuildConfig
import com.hubwallet.apptspos.Utils.Models.CustomerDataList
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistData
import com.hubwallet.apptspos.base.BaseResponse
import com.hubwallet.apptspos.base.LockScreenRes
import com.hubwallet.apptspos.calander.AppointmentListRes
import com.hubwallet.apptspos.calander.change_Status.ChangeStatusRes
import com.hubwallet.apptspos.calander.filter.SearchRes
import com.hubwallet.apptspos.checkout.CheckoutRes
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateDataRes
import com.hubwallet.apptspos.checkout.add_cetificate.templet_res.TempletData
import com.hubwallet.apptspos.checkout.add_product.ProductRes
import com.hubwallet.apptspos.checkout.disscount_res.produc.ProdcutDisscountREs
import com.hubwallet.apptspos.checkout.disscount_res.service.ServicesResponse
import com.hubwallet.apptspos.checkout.payment.RewardRes
import com.hubwallet.apptspos.customer.cust_details.CustDetailsRes
import com.hubwallet.apptspos.employes.details.emp_type.EmpType
import com.hubwallet.apptspos.employes.details.state.StateListRes
import com.hubwallet.apptspos.employes.details.title.TitleRes
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.retrofit.login.PinLoginRes
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    //
    companion object {
        private var apiClient: ApiClient? = null
        var apiAuthHelper: APIAuthHelper? = null

        private const val baseUrl: String = "http://159.203.182.165/"
        fun getApiClientIns(): ApiClient {
            if (apiClient == null) {
                apiClient = ApiClient()
            }
            return apiClient!!
        }
    }

    fun getInstance(): APIAuthHelper {
        if (apiAuthHelper == null) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                // production build
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .callTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
            //            }
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            apiAuthHelper = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(APIAuthHelper::class.java)
        }
        return apiAuthHelper!!
    }

    fun getAppointement(
            vendorId: String
    ): LiveData<AppointmentListRes> {
        val liveData = MutableLiveData<AppointmentListRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getAppointement(vendorId)
                .enqueue(object : CallBackManager<AppointmentListRes>() {
                    override fun onSuccess(response: AppointmentListRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = AppointmentListRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getService(
            vendorId: String
    ): LiveData<ServiceDataRes> {
        val liveData = MutableLiveData<ServiceDataRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getService(vendorId)
                .enqueue(object : CallBackManager<ServiceDataRes>() {
                    override fun onSuccess(response: ServiceDataRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ServiceDataRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getStylist(
            vendorId: String
    ): LiveData<StylistData> {
        val liveData = MutableLiveData<StylistData>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getStylist(vendorId)
                .enqueue(object : CallBackManager<StylistData>() {
                    override fun onSuccess(response: StylistData?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = StylistData()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getEmployeeType(
            vendorId: String
    ): LiveData<EmpType> {
        val liveData = MutableLiveData<EmpType>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getEmployeeType(vendorId)
                .enqueue(object : CallBackManager<EmpType>() {
                    override fun onSuccess(response: EmpType?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = EmpType()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getEmployeeTitle(
            vendorId: String
    ): LiveData<TitleRes> {
        val liveData = MutableLiveData<TitleRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getEmployeeTitle(vendorId)
                .enqueue(object : CallBackManager<TitleRes>() {
                    override fun onSuccess(response: TitleRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = TitleRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getState(
            countryId: String
    ): LiveData<StateListRes> {
        val liveData = MutableLiveData<StateListRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getState(countryId)
                .enqueue(object : CallBackManager<StateListRes>() {
                    override fun onSuccess(response: StateListRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = StateListRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun addEmployee(
            vendorId: String,
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
            photo: String

    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editEmployee(
            vendorId: String,
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
            stylist_id: String

    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo, stylist_id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun addSchedule(
            vendorId: String, stylist_id: String, days: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addSchedule(vendorId, stylist_id, days)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editSchedule(
            vendorId: String, stylist_id: String, days: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editSchedule(vendorId, stylist_id, days)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun addBioData(
            vendorId: String,
            stylist_id: String,
            experienceYear: String,
            experienceMonth: String,
            workLocation: String,
            note: String,
            services: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addBioData(vendorId, stylist_id, experienceYear, experienceMonth, workLocation, note, services)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editBioData(
            vendorId: String,
            stylist_id: String,
            experienceYear: String,
            experienceMonth: String,
            workLocation: String,
            note: String,
            services: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editBioData(vendorId, stylist_id, experienceYear, experienceMonth, workLocation, note, services)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun addEmpService(
            vendorId: String, stylist_id: String, services: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addEmpService(vendorId, stylist_id, services)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editEmpService(
            vendorId: String, stylist_id: String, services: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editEmpService(vendorId, stylist_id, services)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun saveEmpPin(
            vendorId: String, stylist_id: String, pin: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.saveEmpPin(vendorId, stylist_id, pin)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editEmpPin(
            vendorId: String, stylist_id: String, pin: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editEmpPin(vendorId, stylist_id, pin)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addCustomer(vendorId, firstname, lastname, email, mobile, address, zipcode, city_id, state_id, cc_email, birthday, photo, gender, emergency_name, emergency_relation, emergency_contact, anniversary, occupation, referred_by, iou_limit, email_alert, sms_alert, push_notification, allow_online_booking,
                card_holder_name, card_number, cvv, expiry_month, expiry_year)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun editCustomer(vendorId: String,
                     customer_id: String,
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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.editCustomer(vendorId, customer_id, firstname, lastname, email, mobile, address, zipcode, city_id, state_id, cc_email, birthday, photo, gender, emergency_name, emergency_relation, emergency_contact, anniversary, occupation, referred_by, iou_limit, email_alert, sms_alert, push_notification, allow_online_booking,
                card_holder_name, card_number, cvv, expiry_month, expiry_year)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getCustomerDetails(customerId: String
    ): LiveData<CustomerDataList> {
        val liveData = MutableLiveData<CustomerDataList>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getCustomerDetails(customerId)
                .enqueue(object : CallBackManager<CustomerDataList>() {
                    override fun onSuccess(response: CustomerDataList?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = CustomerDataList(null)
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getEmployeeDetails(vendorId: String, customerId: String
    ): LiveData<EmployeeList> {
        val liveData = MutableLiveData<EmployeeList>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getEmployeeDetails(vendorId, customerId)
                .enqueue(object : CallBackManager<EmployeeList>() {
                    override fun onSuccess(response: EmployeeList?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = EmployeeList(null)
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getDepositDetails(customerId: String
    ): LiveData<CustDetailsRes> {
        val liveData = MutableLiveData<CustDetailsRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getDepositDetails(customerId)
                .enqueue(object : CallBackManager<CustDetailsRes>() {
                    override fun onSuccess(response: CustDetailsRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = CustDetailsRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getCustomerList(vendorId: String
    ): LiveData<CustDetailsRes> {
        val liveData = MutableLiveData<CustDetailsRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getCustomerList(vendorId)
                .enqueue(object : CallBackManager<CustDetailsRes>() {
                    override fun onSuccess(response: CustDetailsRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = CustDetailsRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addDeposit(cust_id, vendorId, customer_total, deposit_amount, pool, overdue, iou_limit, chargeAuto, event_name, deposit_type, event_note, start_date, start_time, end_time)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun updateDeposit(depositId: String, cust_id: String,
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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.updateDeposit(depositId, cust_id, vendorId, customer_total, deposit_amount, pool, overdue, iou_limit,
                chargeAuto, event_name, deposit_type, event_note, start_date, start_time, end_time)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun updateDeposit(depositId: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.updateDeposit(depositId)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun addMultipleAppoiment(vendorId: String,
                             appointment_date: String, //comma string
                             appointment_time: String,//comma string
                             service_id: String,//comma string
                             stylist_id: String,//comma string
                             duration: String,//comma string
                             note: String,
                             customer_id: String): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addMultipleAppointment(vendorId, appointment_date, appointment_time, service_id, stylist_id, duration, note, customer_id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData

    }

    fun searchAppointment(vendorId: String,
                          searchVal: String): LiveData<SearchRes> {
        val liveData = MutableLiveData<SearchRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.searchAppointment(vendorId, searchVal)
                .enqueue(object : CallBackManager<SearchRes>() {
                    override fun onSuccess(response: SearchRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = SearchRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun checkoutData(customerId: String,
                     appointmentId: String,
                     type: String,
                     tokenNo: String,
                     vendorId: String,
                     searchValue: String): LiveData<CheckoutRes> {
        val liveData = MutableLiveData<CheckoutRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.checkoutData(customerId, appointmentId, type, tokenNo, vendorId, searchValue)
                .enqueue(object : CallBackManager<CheckoutRes>() {
                    override fun onSuccess(response: CheckoutRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = CheckoutRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun checkoutAddService(customerId: String,
                           date: String,
                           token: String,
                           vendorId: String,
                           aptType: String,
                           appointmentTime: String,
                           duration: String,
                           service: String,
                           stylist1: String,
                           amount_service: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.checkoutAddService(customerId, date, token, vendorId, aptType, appointmentTime, duration, service, stylist1, amount_service)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getTemplates(type: String): LiveData<TempletData> {
        val liveData = MutableLiveData<TempletData>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getTemplates(type)
                .enqueue(object : CallBackManager<TempletData>() {
                    override fun onSuccess(response: TempletData?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = TempletData()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getTemplateData(tempId: String): LiveData<TemplateDataRes> {
        val liveData = MutableLiveData<TemplateDataRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getTemplateData(tempId)
                .enqueue(object : CallBackManager<TemplateDataRes>() {
                    override fun onSuccess(response: TemplateDataRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = TemplateDataRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addCertificate(customerId, ceritificateNumber, service_id, gift_type, expire_date, gift_amount, notifyBy, select_recipient_name, gift_recipient_name,
                gift_recipient_email, messasge, template_id, gift_recipient_phone, template_image_id, vendor_id, login_id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

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
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addGiftCard(customerId, gift_card_number, card_issue_date, card_buyer_name, card_buyer_email, card_phone, card_message, card_amount, notifyBy_card, template_image_id, vendor_id, login_id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getProduct(vendor_id: String
    ): LiveData<ProductRes> {
        val liveData = MutableLiveData<ProductRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getProduct(vendor_id)
                .enqueue(object : CallBackManager<ProductRes>() {
                    override fun onSuccess(response: ProductRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ProductRes()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun addCheckoutproduct(product_id: String,
                           quant: String,
                           customer_id: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addCheckoutproduct(product_id, quant, customer_id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun deleteCheckoutRow(type: String, id: String): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.deleteCheckoutRow(type, id)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun getServiceDiscount(vendorId: String): LiveData<ServicesResponse> {
        val liveData = MutableLiveData<ServicesResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getServiceDiscount(vendorId)
                .enqueue(object : CallBackManager<ServicesResponse>() {
                    override fun onSuccess(response: ServicesResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ServicesResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getProductDiscount(vendorId: String): LiveData<ProdcutDisscountREs> {
        val liveData = MutableLiveData<ProdcutDisscountREs>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getProductDiscount(vendorId)
                .enqueue(object : CallBackManager<ProdcutDisscountREs>() {
                    override fun onSuccess(response: ProdcutDisscountREs?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ProdcutDisscountREs()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getReward(vendorId: String): LiveData<RewardRes> {
        val liveData = MutableLiveData<RewardRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getReward(vendorId)
                .enqueue(object : CallBackManager<RewardRes>() {
                    override fun onSuccess(response: RewardRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = RewardRes()
                        baseResponse.status = statusCode
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun checkCuppon(couponCode: String): LiveData<RewardRes> {
        val liveData = MutableLiveData<RewardRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.checkCuppon(couponCode)
                .enqueue(object : CallBackManager<RewardRes>() {
                    override fun onSuccess(response: RewardRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = RewardRes()
                        baseResponse.status = statusCode
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun checkNumber(type: String, number: String): LiveData<RewardRes> {
        val liveData = MutableLiveData<RewardRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.checkNumber(type, number)
                .enqueue(object : CallBackManager<RewardRes>() {
                    override fun onSuccess(response: RewardRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = RewardRes()
                        baseResponse.status = statusCode
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun getOptionsStatus(type: String): LiveData<ChangeStatusRes> {
        val liveData = MutableLiveData<ChangeStatusRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getOptionsStatus(type)
                .enqueue(object : CallBackManager<ChangeStatusRes>() {
                    override fun onSuccess(response: ChangeStatusRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ChangeStatusRes()
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun changeStatus(type: String, appointment_id: String, colorId: String): LiveData<ChangeStatusRes> {
        val liveData = MutableLiveData<ChangeStatusRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.changeStatus(type, appointment_id, colorId)
                .enqueue(object : CallBackManager<ChangeStatusRes>() {
                    override fun onSuccess(response: ChangeStatusRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = ChangeStatusRes()
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun checkPin(pin: String): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.checkPin(pin)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = -1
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun loginPin(pin: String,fcm_token:String): LiveData<PinLoginRes> {
        val liveData = MutableLiveData<PinLoginRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.loginPin(pin,fcm_token)
                .enqueue(object : CallBackManager<PinLoginRes>() {
                    override fun onSuccess(response: PinLoginRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = PinLoginRes()
                        baseResponse.status = -1
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun screenLockTime(vendorId: String): LiveData<LockScreenRes> {
        val liveData = MutableLiveData<LockScreenRes>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.screenLockTime(vendorId)
                .enqueue(object : CallBackManager<LockScreenRes>() {
                    override fun onSuccess(response: LockScreenRes?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = LockScreenRes()
                        baseResponse.status = -1
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

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
            vendorId: String
    ): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.ordernow(customerId, appointment_id, token, payment_type, tax, tip_amount, cash_value, credit_value, gift_card_value,
                certificate_value, new_gift_card, new_certificate, iou_value, package_amount, membership_val, discount_value, giftcard_db_value,
                gift_card_number, cash_mode_gift, diposite, total_amount, gift_certificate_id, gift_cart_id, service_total_price, vendorId)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }

    fun addstylishgallry(image: String,stylist_id: String,image_name: String,type:String): LiveData<BaseResponse> {
        val liveData = MutableLiveData<BaseResponse>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.addStylishGallery(image, stylist_id, image_name, type)
                .enqueue(object : CallBackManager<BaseResponse>() {
                    override fun onSuccess(response: BaseResponse?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = BaseResponse()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }


    fun getstylishgallry(stylist_id: String,type:String): LiveData<Json4Kotlin_Base> {
        val liveData = MutableLiveData<Json4Kotlin_Base>()
        if (apiAuthHelper == null) {
            getInstance()
        }
        apiAuthHelper!!.getStylishGallery(stylist_id,type)
                .enqueue(object : CallBackManager<Json4Kotlin_Base>() {
                    override fun onSuccess(response: Json4Kotlin_Base?) {
                        liveData.value = response
                    }

                    override fun onFailure(t: Throwable, statusCode: Int) {
                        val baseResponse = Json4Kotlin_Base()
                        baseResponse.status = statusCode
                        baseResponse.message = t.localizedMessage!!
                        liveData.value = baseResponse
                    }

                })
        return liveData
    }
}