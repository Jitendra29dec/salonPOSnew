package com.hubwallet.apptspos.Utils.Models.GroupAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupAppointmentList {

    @SerializedName("result")
    @Expose
    private List<GroupAppointmentData> GroupAppointmentData = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public List<GroupAppointmentData> getGroupAppointmentData() {
        return GroupAppointmentData;
    }

    public void setGroupAppointmentData(List<GroupAppointmentData> GroupAppointmentData) {
        this.GroupAppointmentData = GroupAppointmentData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}