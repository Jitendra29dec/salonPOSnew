package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreakData {
    @SerializedName("attendance_id")
    @Expose
    private String attendanceId;
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;
    @SerializedName("attendance_time")
    @Expose
    private String attendanceTime;
    @SerializedName("attendence_back_time")
    @Expose
    private String attendenceBackTime;
    @SerializedName("total_hours")
    @Expose
    private String totalHours;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getAttendenceBackTime() {
        return attendenceBackTime;
    }

    public void setAttendenceBackTime(String attendenceBackTime) {
        this.attendenceBackTime = attendenceBackTime;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
