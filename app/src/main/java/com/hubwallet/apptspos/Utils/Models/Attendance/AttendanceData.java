package com.hubwallet.apptspos.Utils.Models.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceData {
    @SerializedName("attendance_id")
    @Expose
    private String attendanceId;
    @SerializedName("stylist_id")
    @Expose
    private String stylistId;
    @SerializedName("dt1")
    @Expose
    private String dt1;
    @SerializedName("tm")
    @Expose
    private String tm;
    @SerializedName("title_name")
    @Expose
    private String titleName;
    @SerializedName("stylist_name")
    @Expose
    private String stylistName;
    @SerializedName("clockin")
    @Expose
    private String clockin;
    @SerializedName("clockout")
    @Expose
    private String clockout;
    @SerializedName("breaklev")
    @Expose
    private Object breaklev;
    @SerializedName("breakback")
    @Expose
    private Object breakback;
    @SerializedName("emergency_clock")
    @Expose
    private String emergencyClock;
    @SerializedName("sch_from_time")
    @Expose
    private String schFromTime;
    @SerializedName("sch_to_time")
    @Expose
    private String schToTime;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getDt1() {
        return dt1;
    }

    public void setDt1(String dt1) {
        this.dt1 = dt1;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
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

    public Object getBreaklev() {
        return breaklev;
    }

    public void setBreaklev(Object breaklev) {
        this.breaklev = breaklev;
    }

    public Object getBreakback() {
        return breakback;
    }

    public void setBreakback(Object breakback) {
        this.breakback = breakback;
    }

    public String getEmergencyClock() {
        return emergencyClock;
    }

    public void setEmergencyClock(String emergencyClock) {
        this.emergencyClock = emergencyClock;
    }

    public String getSchFromTime() {
        return schFromTime;
    }

    public void setSchFromTime(String schFromTime) {
        this.schFromTime = schFromTime;
    }

    public String getSchToTime() {
        return schToTime;
    }

    public void setSchToTime(String schToTime) {
        this.schToTime = schToTime;
    }
}
