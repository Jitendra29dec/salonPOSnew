package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreakLeave {
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;
    @SerializedName("total_break")
    @Expose
    private String totalBreak;

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getTotalBreak() {
        return totalBreak;
    }

    public void setTotalBreak(String totalBreak) {
        this.totalBreak = totalBreak;
    }
}
