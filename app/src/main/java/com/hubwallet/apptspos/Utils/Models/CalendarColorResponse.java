package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;

import java.util.List;

public class CalendarColorResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<CalendarColorData> result = null;

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

    public List<CalendarColorData> getResult() {
        return result;
    }

    public void setResult(List<CalendarColorData> result) {
        this.result = result;
    }
}
