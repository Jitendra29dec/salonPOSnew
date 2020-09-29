package com.hubwallet.apptspos.Utils.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceList {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("attendance")
    @Expose
    private List<AttendanceData> attendance = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AttendanceData> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AttendanceData> attendance) {
        this.attendance = attendance;
    }


}
