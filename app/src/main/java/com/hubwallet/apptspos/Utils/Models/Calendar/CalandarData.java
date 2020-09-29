
package com.hubwallet.apptspos.Utils.Models.Calendar;

import com.google.gson.annotations.SerializedName;

public class CalandarData {

    @SerializedName("business_hour_id")
    private String mBusinessHourId;
    @SerializedName("days")
    private String mDays;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("start_time_format")
    private String mStartTimeFormat;
    @SerializedName("switch")
    private String mSwitch;
    @SerializedName("vendor_id")
    private String mVendorId;
    @SerializedName("end_time")
    private String mEndTime;

    public String getmBusinessHourId() {
        return mBusinessHourId;
    }

    public void setmBusinessHourId(String mBusinessHourId) {
        this.mBusinessHourId = mBusinessHourId;
    }

    public String getmDays() {
        return mDays;
    }

    public void setmDays(String mDays) {
        this.mDays = mDays;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmStartTimeFormat() {
        return mStartTimeFormat;
    }

    public void setmStartTimeFormat(String mStartTimeFormat) {
        this.mStartTimeFormat = mStartTimeFormat;
    }

    public String getmSwitch() {
        return mSwitch;
    }

    public void setmSwitch(String mSwitch) {
        this.mSwitch = mSwitch;
    }

    public String getmVendorId() {
        return mVendorId;
    }

    public void setmVendorId(String mVendorId) {
        this.mVendorId = mVendorId;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

}
