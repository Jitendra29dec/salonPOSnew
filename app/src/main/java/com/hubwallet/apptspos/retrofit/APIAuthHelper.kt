package com.hubwallet.apptspos.retrofit

import EmployeeList
import Json4Kotlin_Base
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
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIAuthHelper {
    @POST("salon/api/appointment/show")
    @FormUrlEncoded
    fun getAppointement(@Field("vendor_id") vendorId: String): Call<AppointmentListRes>

    @POST("salon/api/service/get")
    @FormUrlEncoded
    fun getService(@Field("vendor_id") vendorId: String): Call<ServiceDataRes>

    @POST("salon/api/stylist/get")
    @FormUrlEncoded
    fun getStylist(@Field("vendor_id") vendorId: String): Call<StylistData>

    @POST("salon/api/stylist/employeeType")
    @FormUrlEncoded
    fun getEmployeeType(@Field("vendor_id") vendorId: String): Call<EmpType>

    @POST("salon/api/stylist/employeeTitle")
    @FormUrlEncoded
    fun getEmployeeTitle(@Field("vendor_id") vendorId: String): Call<TitleRes>

    @POST("salon/api/customer/getState")
    @FormUrlEncoded
    fun getState(@Field("country_id") vendorId: String): Call<StateListRes>

    @POST("salon/api/stylist/add")
    @FormUrlEncoded
    fun addEmployee(@Field("vendor_id") vendorId: String,
                    @Field("emp_type_id") emp_type_id: String,
                    @Field("title_id") title_id: String,
                    @Field("firstname") firstname: String,
                    @Field("lastname") lastname: String,
                    @Field("email") email: String,
                    @Field("phone") phone: String,
                    @Field("alternate_phone") alternate_phone: String,
                    @Field("state_id") state_id: String,
                    @Field("city") city: String,
                    @Field("address") address: String,
                    @Field("photo") photo: String
    ): Call<BaseResponse>

    @POST("salon/api/stylist/edit")
    @FormUrlEncoded
    fun editEmployee(@Field("vendor_id") vendorId: String,
                     @Field("emp_type_id") emp_type_id: String,
                     @Field("title_id") title_id: String,
                     @Field("firstname") firstname: String,
                     @Field("lastname") lastname: String,
                     @Field("email") email: String,
                     @Field("phone") phone: String,
                     @Field("alternate_phone") alternate_phone: String,
                     @Field("state_id") state_id: String,
                     @Field("city") city: String,
                     @Field("address") address: String,
                     @Field("photo") photo: String,
                     @Field("stylist_id") stylist_id: String
    ): Call<BaseResponse>

    @POST("salon/api/stylist/employeeSchedule")
    @FormUrlEncoded
    fun addSchedule(@Field("vendor_id") vendorId: String,
                    @Field("stylist_id") stylist_id: String,
                    @Field("days") days: String): Call<BaseResponse>


    @POST("salon/api/stylist/employeeSchedule")
    @FormUrlEncoded
    fun editSchedule(@Field("vendor_id") vendorId: String,
                     @Field("stylist_id") stylist_id: String,
                     @Field("days") days: String): Call<BaseResponse>


    @POST("salon/api/stylist/biodata")
    @FormUrlEncoded
    fun addBioData(@Field("vendor_id") vendorId: String,
                   @Field("stylist_id") stylist_id: String,
                   @Field("experience_year") experienceYear: String,
                   @Field("experience_month") experienceMonth: String,
                   @Field("work_location") workLocation: String,
                   @Field("note") note: String,
                   @Field("services") services: String): Call<BaseResponse>

    @POST("salon/api/stylist/biodata")
    @FormUrlEncoded
    fun editBioData(@Field("vendor_id") vendorId: String,
                    @Field("stylist_id") stylist_id: String,
                    @Field("experience_year") experienceYear: String,
                    @Field("experience_month") experienceMonth: String,
                    @Field("work_location") workLocation: String,
                    @Field("note") note: String,
                    @Field("services") services: String): Call<BaseResponse>

    @POST("salon/api/stylist/employeeServices")
    @FormUrlEncoded
    fun addEmpService(@Field("vendor_id") vendorId: String, @Field("stylist_id") stylist_id: String,
                      @Field("services") days: String): Call<BaseResponse>

    @POST("salon/api/stylist/editEmployeeServices")
    @FormUrlEncoded
    fun editEmpService(@Field("vendor_id") vendorId: String,
                       @Field("stylist_id") stylist_id: String,
                       @Field("services") days: String): Call<BaseResponse>

    @POST("salon/api/stylist/pin")
    @FormUrlEncoded
    fun saveEmpPin(@Field("vendor_id") vendorId: String, @Field("stylist_id") stylist_id: String,
                       @Field("pin") pin: String): Call<BaseResponse>

    @POST("salon/api/stylist/pin")
    @FormUrlEncoded
    fun editEmpPin(@Field("vendor_id") vendorId: String, @Field("stylist_id") stylist_id: String,
                   @Field("pin") pin: String): Call<BaseResponse>


    @POST("salon/api/customer/add")
    @FormUrlEncoded
    fun addCustomer(@Field("vendor_id") vendorId: String,
                    @Field("firstname") firstname: String,
                    @Field("lastname") lastname: String,
                    @Field("email") email: String,
                    @Field("mobile") mobile: String,
                    @Field("address") address: String,
                    @Field("zipcode") zipcode: String,
                    @Field("city") city_id: String,
                    @Field("state_id") state_id: String,
                    @Field("cc_email") cc_email: String,
                    @Field("birthday") birthday: String,
                    @Field("photo") photo: String,
                    @Field("gender") gender: String,
                    @Field("emergency_name") emergency_name: String,
                    @Field("emergency_relation") emergency_relation: String,
                    @Field("emergency_contact") emergency_contact: String,
                    @Field("anniversary") anniversary: String,
                    @Field("occupation") occupation: String,
                    @Field("referred_by") referred_by: String,
                    @Field("iou_limit") iou_limit: String,
                    @Field("email_alert") email_alert: String,
                    @Field("sms_alert") sms_alert: String,
                    @Field("push_notification") push_notification: String,
                    @Field("allow_online_booking") allow_online_booking: String,
                    @Field("card_holder_name") card_holder_name: String,
                    @Field("card_number") card_number: String,
                    @Field("cvv") cvv: String,
                    @Field("expiry_month") expiry_month: String,
                    @Field("expiry_year") expiry_year: String
    ): Call<BaseResponse>

    @POST("salon/api/customer/edit")
    @FormUrlEncoded
    fun editCustomer(@Field("vendor_id") vendorId: String,
                     @Field("customer_id") customer_id: String,
                     @Field("firstname") firstname: String,
                     @Field("lastname") lastname: String,
                     @Field("email") email: String,
                     @Field("mobile") mobile: String,
                     @Field("address") address: String,
                     @Field("zipcode") zipcode: String,
                     @Field("city") city_id: String,
                     @Field("state_id") state_id: String,
                     @Field("cc_email") cc_email: String,
                     @Field("birthday") birthday: String,
                     @Field("photo") photo: String,
                     @Field("gender") gender: String,
                     @Field("emergency_name") emergency_name: String,
                     @Field("emergency_relation") emergency_relation: String,
                     @Field("emergency_contact") emergency_contact: String,
                     @Field("anniversary") anniversary: String,
                     @Field("occupation") occupation: String,
                     @Field("referred_by") referred_by: String,
                     @Field("iou_limit") iou_limit: String,
                     @Field("email_alert") email_alert: String,
                     @Field("sms_alert") sms_alert: String,
                     @Field("push_notification") push_notification: String,
                     @Field("allow_online_booking") allow_online_booking: String,
                     @Field("card_holder_name") card_holder_name: String,
                     @Field("card_number") card_number: String,
                     @Field("cvv") cvv: String,
                     @Field("expiry_month") expiry_month: String,
                     @Field("expiry_year") expiry_year: String

    ): Call<BaseResponse>

    @POST("salon/api/customer/getCustomerById")
    @FormUrlEncoded
    fun getCustomerDetails(@Field("customer_id") customerId: String): Call<CustomerDataList>

    @POST("salon/api/stylist/getStylistById")
    @FormUrlEncoded
    fun getEmployeeDetails(@Field("vendor_id") vendor_id: String,
                           @Field("stylist_id") stylist_id: String): Call<EmployeeList>

    @POST("salon/api/customer/getCustomerById")
    @FormUrlEncoded
    fun getDepositDetails(@Field("customer_id") customerId: String): Call<CustDetailsRes>

    @POST("salon/api/customer/get")
    @FormUrlEncoded
    fun getCustomerList(@Field("vendor_id") vendorId: String): Call<CustDetailsRes>

    @POST("salon/api/appointment/add_deposit")
    @FormUrlEncoded
    fun addDeposit(@Field("cust_id") cust_id: String,
                   @Field("vendor_id") vendor_id: String,
                   @Field("customer_total") customer_total: String,
                   @Field("deposit_amount") deposit_amount: String,
                   @Field("pool") pool: String,
                   @Field("overdue") overdue: String,
                   @Field("iou_limit") iou_limit: String,
                   @Field("charge_automatic") charge_automatic: String,
                   @Field("event_name") event_name: String,
                   @Field("deposit_type") deposit_type: String,
                   @Field("event_note") event_note: String,
                   @Field("start_date") start_date: String,
                   @Field("start_time") start_time: String,
                   @Field("end_time") end_time: String
    ): Call<BaseResponse>

    @POST("salon/api/appointment/add_deposit")
    @FormUrlEncoded
    fun updateDeposit(@Field("deposit_last_id") depositId: String,
                      @Field("cust_id") cust_id: String,
                      @Field("vendor_id") vendor_id: String,
                      @Field("customer_total") customer_total: String,
                      @Field("deposit_amount") deposit_amount: String,
                      @Field("pool") pool: String,
                      @Field("overdue") overdue: String,
                      @Field("iou_limit") iou_limit: String,
                      @Field("charge_automatic") charge_automatic: String,
                      @Field("event_name") event_name: String,
                      @Field("deposit_type") deposit_type: String,
                      @Field("event_note") event_note: String,
                      @Field("start_date") start_date: String,
                      @Field("start_time") start_time: String,
                      @Field("end_time") end_time: String
    ): Call<BaseResponse>

    @POST("salon/api/appointment/update_deposit")
    @FormUrlEncoded
    fun updateDeposit(@Field("deposit_last_id") depositId: String): Call<BaseResponse>

    @POST("salon/api/appointment/addMultiple")
    @FormUrlEncoded
    fun addMultipleAppointment(@Field("vendor_id") vendorId: String,
                               @Field("appointment_date") appointment_date: String, //comma string
                               @Field("appointment_time") appointment_time: String,//comma string
                               @Field("service_id") service_id: String,//comma string
                               @Field("stylist_id") stylist_id: String,//comma string
                               @Field("duration") duration: String,//comma string
                               @Field("note") note: String,
                               @Field("customer_id") customer_id: String
    ): Call<BaseResponse>

    @POST("salon/api/appointment/searchAppointment")
    @FormUrlEncoded
    fun searchAppointment(@Field("vendor_id") vendorId: String,
                          @Field("search_val") search: String
    ): Call<SearchRes>

    @POST("salon/api/checkout/getcheckoutData")
    @FormUrlEncoded
    fun checkoutData(@Field("customer_id") customerId: String,
                     @Field("appointment_id") appointmentId: String,
                     @Field("appointment_type") type: String,
                     @Field("token_no") tokenNo: String,
                     @Field("vendor_id") vendorId: String,
                     @Field("search") searchValue: String
    ): Call<CheckoutRes>

    @POST("salon/api/checkout/add_service")
    @FormUrlEncoded
    fun checkoutAddService(@Field("customer_id") customerId: String,
                           @Field("appointment_date") date: String,
                           @Field("token_no") token: String,
                           @Field("vendor_id") vendorId: String,
                           @Field("appointment_type") aptType: String,
                           @Field("appointment_time") appointmentTime: String,
                           @Field("duration") duration: String,
                           @Field("service") service: String,
                           @Field("stylist1") stylist1: String,
                           @Field("amount_service") amount_service: String
    ): Call<BaseResponse>

    @POST("salon/api/checkout/getTemplates")
    @FormUrlEncoded
    fun getTemplates(@Field("type") type: String
    ): Call<TempletData>

    @POST("salon/api/checkout/getTemplateData")
    @FormUrlEncoded
    fun getTemplateData(@Field("temp_id") tempId: String
    ): Call<TemplateDataRes>

    @POST("salon/api/checkout/add_certificate")
    @FormUrlEncoded
    fun addCertificate(@Field("customer_id") customerId: String,
                       @Field("gift_certificate_number") ceritificateNumber: String,
                       @Field("service_id") service_id: String,
                       @Field("gift_type") gift_type: String,
                       @Field("expire_date") expire_date: String,
                       @Field("gift_amount") gift_amount: String,
                       @Field("notifyBy") notifyBy: String,
                       @Field("select_recipient_name") select_recipient_name: String,
                       @Field("gift_recipient_name") gift_recipient_name: String,
                       @Field("gift_recipient_email") gift_recipient_email: String,
                       @Field("messasge") messasge: String,
                       @Field("template_id") template_id: String,
                       @Field("gift_recipient_phone") gift_recipient_phone: String,
                       @Field("template_image_id") template_image_id: String,
                       @Field("vendor_id") vendor_id: String,
                       @Field("login_id") login_id: String
    ): Call<BaseResponse>

    @POST("salon/api/checkout/add_gift_card")
    @FormUrlEncoded
    fun addGiftCard(@Field("card_customer_id") customerId: String,
                    @Field("gift_card_number") gift_card_number: String,
                    @Field("card_issue_date") card_issue_date: String,
                    @Field("card_buyer_name") card_buyer_name: String,
                    @Field("card_buyer_email") card_buyer_email: String,
                    @Field("card_phone") card_phone: String,
                    @Field("card_message") card_message: String,
                    @Field("card_amount") card_amount: String,
                    @Field("notifyBy_card") notifyBy_card: String,
                    @Field("template_image_id") template_image_id: String,
                    @Field("vendor_id") vendor_id: String,
                    @Field("login_id") login_id: String
    ): Call<BaseResponse>

    @POST("salon/api/product/getProduct")
    @FormUrlEncoded
    fun getProduct(
            @Field("vendor_id") vendor_id: String
    ): Call<ProductRes>

    @POST("salon/api/checkout/addProduct")
    @FormUrlEncoded
    fun addCheckoutproduct(
            @Field("product_id") product_id: String,
            @Field("quant") quant: String,
            @Field("customer_id") customer_id: String
    ): Call<BaseResponse>

    @POST("salon/api/checkout/removeRow")
    @FormUrlEncoded
    fun deleteCheckoutRow(
            @Field("type") product_id: String,
            @Field("row_id") quant: String
    ): Call<BaseResponse>

    @POST("salon/api/checkout/getServiceDiscount")
    @FormUrlEncoded
    fun getServiceDiscount(
            @Field("vendor_id") vendor_id: String
    ): Call<ServicesResponse>

    @POST("salon/api/checkout/getProductDiscount")
    @FormUrlEncoded
    fun getProductDiscount(
            @Field("vendor_id") vendor_id: String
    ): Call<ProdcutDisscountREs>

    @POST("salon/api/checkout/customer_rewards")
    @FormUrlEncoded
    fun getReward(
            @Field("customer_id") customer_id: String
    ): Call<RewardRes>

    @POST("salon/api/checkout/checkcuppon")
    @FormUrlEncoded
    fun checkCuppon(
            @Field("cupponCode") cupponCode: String
    ): Call<RewardRes>

    @POST("salon/api/checkout/check_number")
    @FormUrlEncoded
    fun checkNumber(
            @Field("type") type: String,
            @Field("number_check") number_check: String
    ): Call<RewardRes>

    @POST("salon/api/appointment/getOptions")
    @FormUrlEncoded
    fun getOptionsStatus(
            @Field("vendor_id") vendorId: String
    ): Call<ChangeStatusRes>

    @POST("salon/api/appointment/changeStatus")
    @FormUrlEncoded
    fun changeStatus(
            @Field("type") type: String,
            @Field("appointment_id") appointment_id: String,
            @Field("color_id") color_id: String
    ): Call<ChangeStatusRes>

    @POST("salon/api/checkout/checkpin")
    @FormUrlEncoded
    fun checkPin(
            @Field("pin") pin: String
    ): Call<BaseResponse>

    @POST("salon/api/login/LoginPin")
    @FormUrlEncoded
    fun loginPin(
            @Field("pin") pin: String,
            @Field("fcm_token") fcm_token:String,
    ): Call<PinLoginRes>

    @POST("salon/api/login/screenLocktime")
    @FormUrlEncoded
    fun screenLockTime(
            @Field("vendor_id") vendorId: String
    ): Call<LockScreenRes>

    @POST("salon/api/checkout/ordernow")
    @FormUrlEncoded
    fun ordernow(
            @Field("customer_id") customerId: String,
            @Field("appointment_id") appointment_id: String,
            @Field("token") token: String,
            @Field("payment_type") payment_type: String,
            @Field("tax") tax: String,
            @Field("tip_amount") tip_amount: String,
            @Field("reward_value") cash_value: String,
            @Field("credit_value") credit_value: String,
            @Field("gift_card_value") gift_card_value: String,
            @Field("certificate_value") certificate_value: String,
            @Field("new_gift_card") new_gift_card: String,
            @Field("new_certificate") new_certificate: String,
            @Field("iou_value") iou_value: String,
            @Field("package_amount") package_amount: String,
            @Field("membership_val") membership_val: String,
            @Field("discount_value") discount_value: String,
            @Field("giftcard_db_value") giftcard_db_value: String,
            @Field("gift_card_number") gift_card_number: String,
            @Field("cash_mode_gift") cash_mode_gift: String,
            @Field("diposite") diposite: String,
            @Field("total_amount") total_amount: String,
            @Field("gift_certificate_id") gift_certificate_id: String,
            @Field("gift_cart_id") gift_cart_id: String,
            @Field("service_total_price") service_total_price: String,
            @Field("vendor_id") vendorId: String
    ): Call<BaseResponse>



    @POST("salon/api/stylist/add_gallery")
    @FormUrlEncoded
    fun addStylishGallery(@Field("image") image: String,
                   @Field("stylist_id") stylist_id: String,
                   @Field("image_name") image_name: String,
                   @Field("type") type: String): Call<BaseResponse>


    @POST("salon/api/stylist/getStylistGallery")
    @FormUrlEncoded
    fun getStylishGallery(@Field("stylist_id") stylist_id: String,
                          @Field("type") type: String): Call<Json4Kotlin_Base>
}