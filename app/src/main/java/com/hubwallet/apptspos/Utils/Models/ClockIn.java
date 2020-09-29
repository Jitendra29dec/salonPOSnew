package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClockIn {
    @SerializedName("attendance_id")
    @Expose
    private String attendanceId;
    @SerializedName("clockin")
    @Expose
    private String clockin;
    @SerializedName("clockout")
    @Expose
    private String clockout;
    @SerializedName("total_hours")
    @Expose
    private String totalHours;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getClockin() {
        return clockin;
    }

    public void setClockin(String clockin) {
        this.clockin = clockin;
    }

    public String getClockout() {
        return clockout;
    }

    public void setClockout(String clockout) {
        this.clockout = clockout;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
