package com.hubwallet.apptspos.Utils.Models.OrderDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("login_id")
    @Expose
    private String loginId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("home_phone")
    @Expose
    private String homePhone;
    @SerializedName("mobile_phone")
    @Expose
    private String mobilePhone;
    @SerializedName("work_phone")
    @Expose
    private String workPhone;
    @SerializedName("prefered_contact")
    @Expose
    private String preferedContact;
    @SerializedName("cc_appointment_email")
    @Expose
    private String ccAppointmentEmail;
    @SerializedName("is_email_receive")
    @Expose
    private String isEmailReceive;
    @SerializedName("is_sms_receive")
    @Expose
    private String isSmsReceive;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("anniversary")
    @Expose
    private String anniversary;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("customer_since")
    @Expose
    private String customerSince;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("referred_by")
    @Expose
    private String referredBy;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("emergency_contact_name")
    @Expose
    private String emergencyContactName;
    @SerializedName("emergency_relationship")
    @Expose
    private String emergencyRelationship;
    @SerializedName("emergency_phone")
    @Expose
    private String emergencyPhone;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("refered_by")
    @Expose
    private String referedBy;
    @SerializedName("note")
    @Expose
    private String note;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getPreferedContact() {
        return preferedContact;
    }

    public void setPreferedContact(String preferedContact) {
        this.preferedContact = preferedContact;
    }

    public String getCcAppointmentEmail() {
        return ccAppointmentEmail;
    }

    public void setCcAppointmentEmail(String ccAppointmentEmail) {
        this.ccAppointmentEmail = ccAppointmentEmail;
    }

    public String getIsEmailReceive() {
        return isEmailReceive;
    }

    public void setIsEmailReceive(String isEmailReceive) {
        this.isEmailReceive = isEmailReceive;
    }

    public String getIsSmsReceive() {
        return isSmsReceive;
    }

    public void setIsSmsReceive(String isSmsReceive) {
        this.isSmsReceive = isSmsReceive;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(String anniversary) {
        this.anniversary = anniversary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCustomerSince() {
        return customerSince;
    }

    public void setCustomerSince(String customerSince) {
        this.customerSince = customerSince;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public void setEmergencyRelationship(String emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
