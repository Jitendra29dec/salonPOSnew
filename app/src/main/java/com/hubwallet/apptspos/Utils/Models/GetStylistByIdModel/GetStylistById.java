package com.hubwallet.apptspos.Utils.Models.GetStylistByIdModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStylistById {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("service")
    @Expose
    private List<Service> service = null;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = null;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

}
