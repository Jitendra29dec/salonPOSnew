package com.hubwallet.apptspos.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalendarJson {
   private String _switch;
    private String day;
    private String from;
    private String to;

    public CalendarJson(String _switch,String day,String from,String to){
        this._switch = _switch;
        this.day = day;
        this.from = from;
        this.to = to;
    }

   /* @SerializedName("switch")
    @Expose
    private String _switch;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;*/

    public String getSwitch() {
        return _switch;
    }

    public void setSwitch(String _switch) {
        this._switch = _switch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
