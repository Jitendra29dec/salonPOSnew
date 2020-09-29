package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreakLeaveData {
    @SerializedName("clockin")
    @Expose
    private List<ClockIn> clockin = null;
    @SerializedName("break_leave")
    @Expose
    private List<BreakLeave> breakLeave = null;

    public List<ClockIn> getClockin() {
        return clockin;
    }

    public void setClockin(List<ClockIn> clockin) {
        this.clockin = clockin;
    }

    public List<BreakLeave> getBreakLeave() {
        return breakLeave;
    }

    public void setBreakLeave(List<BreakLeave> breakLeave) {
        this.breakLeave = breakLeave;
    }
}
